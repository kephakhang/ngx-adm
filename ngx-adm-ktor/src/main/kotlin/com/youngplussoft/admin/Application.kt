@file:Suppress("NAME_SHADOWING")

package com.youngplussoft.adm

import com.youngplussoft.admin.common.BackgroundJob
import com.youngplussoft.admin.common.KeyGenerator
import com.youngplussoft.admin.exception.YpsException
import com.youngplussoft.admin.exception.ErrorCode
import com.youngplussoft.admin.extensions.stackTraceString
import com.youngplussoft.admin.server.auth.JwtConfig
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.own.dto.Response
import com.youngplussoft.admin.server.jpa.own.dto.UserDto
import com.youngplussoft.admin.server.jpa.own.entity.Member
import com.youngplussoft.admin.server.jpa.own.repository.*
import com.youngplussoft.admin.server.route.*
import com.youngplussoft.admin.server.service.*
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.http.content.*
import io.ktor.server.locations.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.*
import io.ktor.server.plugins.dataconversion.*
import io.ktor.server.plugins.forwardedheaders.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.webjars.*
import io.ktor.server.websocket.*
import io.ktor.util.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mu.KotlinLogging
import java.time.Duration
import java.time.ZonedDateTime
import kotlin.concurrent.thread

fun main(args: Array<String>): Unit = io.ktor.server.jetty.EngineMain.main(args)

private val logger = KotlinLogging.logger {}

@KtorExperimentalAPI
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    val applicable: Boolean =
        environment.config.config("ktor.deployment").property("applicable").getString().toBoolean()

    if (testing || !applicable) {
        return
    }

    Env.branch = environment.config.config("ktor.deployment").property("branch").getString()
    Env.adminServerUrl = environment.config.config("ktor.deployment").property("serverUrl").getString()

    val monitor = ApplicationEvents()

    val started: (Application) -> Unit = {
        logger.debug(Env.message("app.main.start"), it)
    }

    var stopped: (Application) -> Unit = {}
    stopped = {
        monitor.unsubscribe(ApplicationStarted, started)
        monitor.unsubscribe(ApplicationStopped, stopped)
        logger.debug(Env.message("app.main.stop"), it)
    }

    monitor.subscribe(ApplicationStarted, started)
    monitor.subscribe(ApplicationStopped, stopped)

    Env.initDB(environment.config.config("ktor.db"))
    Env.initMailSender(environment.config.config("ktor.mail"))


    install(Compression)
    install(Locations)

    install(CORS) {
        anyHost()
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.XForwardedProto)
//    header("MyCustomHeader")
        allowCredentials = true
        allowCredentials = true
        allowNonSimpleContentTypes = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

   install(CachingHeaders) {
       options { call, outgoingContent ->
           when (outgoingContent.contentType?.withoutParameters()) {
               ContentType.Text.CSS -> CachingOptions(
                   cacheControl = CacheControl.MaxAge(maxAgeSeconds = 3600),
                   expires = ZonedDateTime.now()
               )
               ContentType.Application.Json -> CachingOptions(
                   cacheControl = CacheControl.MaxAge(maxAgeSeconds = 60),
                   expires = ZonedDateTime.now()
               )
               else -> null
           }
       }
   }

    install(DataConversion)


    install(Webjars) {
        path = "/webjars" //defaults to /webjars /defaults to UTC zone
    }


    install(ContentNegotiation) {
        jackson {
            registerModule(JavaTimeModule())
            enable(SerializationFeature.INDENT_OUTPUT)
            setSerializationInclusion(JsonInclude.Include.NON_NULL)
        }
    }

    // https://ktor.io/servers/features/forward-headers.html
    install(ForwardedHeaders)

    install(io.ktor.server.websocket.WebSockets) {
        //ToDo 상용 배포 시 ping 값 주어야 함
        pingPeriod = Duration.ofSeconds(30)
        timeout = Duration.ofSeconds(30)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    logger.debug(Env.message("app.websocket.install"))


    //ref : https://ktor.io/docs/call-id.html
    install(CallId) {

        retrieveFromHeader(HttpHeaders.XRequestId)

        generate { KeyGenerator.generateOrderNo() }

        verify { it.isNotEmpty() }
    }

    install(CallLogging) {
        //level = Level.INFO
        mdc(HttpHeaders.XRequestId) { call ->
            call.request.header(HttpHeaders.XRequestId)
        }
        //filter { call -> call.request.path().startsWith("/v1/user/push") }
    }

    val adminService: AdminService = AdminService(AdminRepository())
    val userService: UserService = UserService(MemberRepository())
    val ssoService: SsoService = SsoService(MemberRepository(), MemberDetailRepository())
    val tenantService: TenantService = TenantService(TenantRepository())
    val terminalService: TerminalService = TerminalService(TerminalRepository(), CounterRepository())
    val counterService: CounterService = CounterService(CounterRepository())

    // ref : https://github.com/AndreasVolkmann/realworld-kotlin-ktor
    install(Authentication) {
        jwt("api") {
            authSchemes()
            verifier(JwtConfig.verifier)
            realm = JwtConfig.realm
            validate {
                it.payload.claims.forEach(::println)
                val uid = it.payload.getClaim("uid")?.asString() ?: return@validate null
                logger.debug("Required: $uid")
                userService.get(uid).let { member: Member ->
                    member.toDto().copy(token = it.payload)
                }
            }
        }
    }

    // ref : https://ktor.io/docs/status-pages.html#redirect
    install(StatusPages) {
        exception<YpsException> { call, cause ->
            logger.error("routing error : ${cause.stackTraceString}")
            val tid = call.callId
            val isException = true
            val err = Env.error(cause)
            val requestUri: String = call.request.uri
            val method: String = call.request.httpMethod.value
            val response = Response(err as Any, tid, requestUri, method.uppercase())
            val session: UserDto? = call.request.call.authentication.principal<UserDto>()
            if (session != null) {
                call.response.header(JwtConfig.authHeader, JwtConfig.makeToken(session, session.token?.id))
            }

            call.respond(HttpStatusCode(err.status, err.description), response)

            if (logger.isDebugEnabled) {
                loggging(
                    requestUri,
                    isException,
                    session,
                    call.request,
                    call.response
                )
            }
        }
        exception<Throwable> { call, cause ->
            logger.error("routing error : ${cause.stackTraceString}")
            val tid = call.callId
            val isException = true
            val err = Env.error(ErrorCode.E00000)
            err.description = cause.localizedMessage
            val requestUri: String = call.request.uri
            val method: String = call.request.httpMethod.value
            val response = Response(err as Any, tid, requestUri, method.uppercase())
            val session: UserDto? = call.request.call.authentication.principal<UserDto>()
            if (session != null) {
                call.response.header(JwtConfig.authHeader, JwtConfig.makeToken(session, session.token?.id))
            }

            call.respond(HttpStatusCode(err.status, err.description), response)

            if (logger.isDebugEnabled) {
                loggging(
                    requestUri,
                    isException,
                    session,
                    call.request,
                    response
                )
            }
        }
    }

    // ref : https://ktor.kotlincn.net/advanced/pipeline/route.html
    val callMonitor = ApplicationEvents()
    // AOP::beforeCall()
    callMonitor.subscribe(Routing.RoutingCallStarted) { call: RoutingApplicationCall ->
        logger.debug("### Route started: ${call.route} : ${call.callId} [")
        val uri = call.request.uri
        val response = call.response
        when (call.request.httpMethod.value.uppercase()) {
            "OPTIONS" -> {
                if (uri.startsWith("/api/")) {
                    response.header("Access-Control-Allow-Origin", "*")
                    response.header("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT")
                    response.header("Access-Control-Max-Age", "3600")
                    response.header(
                        "Access-Control-Allow-Headers",
                        "X-Requested-With, sessionKey, Cache-Control, Content-Type, Accept, Authorization"
                    )
                    response.header("Content-Type", "application/json; charset=utf-8")

                    GlobalScope.launch {
                        call.respond(HttpStatusCode.OK, "{\"success\":true}")
                    }
                } else {
                    response.header("Access-Control-Allow-Origin", "*")
                    response.header("Access-Control-Allow-Methods", "")
                    response.header("Access-Control-Max-Age", "10")
                    response.header("Content-Type", "application/json; charset=utf-8")
                    GlobalScope.launch {
                        call.respond(HttpStatusCode.Unauthorized, "{\"success\":false}")
                    }
                }

            }
            else -> {

                if (uri.startsWith("/api/")) {
                    response.header("Access-Control-Allow-Origin", "*")
                    response.header("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT")
                    response.header("Access-Control-Max-Age", "3600")
                    response.header(
                        "Access-Control-Allow-Headers",
                        "X-Requested-With, sessionKey, Cache-Control, Content-Type, Accept, Authorization"
                    )
                    response.header("Content-Type", "application/json; charset=utf-8")
                } else {
                    response.header("Access-Control-Allow-Origin", "*")
                    response.header("Access-Control-Allow-Methods", "")
                    response.header("Access-Control-Max-Age", "10")
                    response.header("Content-Type", "application/json; charset=utf-8")
                    GlobalScope.launch {
                        call.respond(HttpStatusCode.Unauthorized, "{\"success\":false}")
                    }
                }
            }
        }
    }

    // AOP::afterCall()
    callMonitor.subscribe(Routing.RoutingCallFinished) { call: RoutingApplicationCall ->
        val isException = true
        val requestUri: String = call.request.uri
        val session: UserDto? = call.request.call.authentication.principal<UserDto>()
        if (session != null) {
            call.response.header(JwtConfig.authHeader, JwtConfig.makeToken(session, session.token?.id))
        }

        if (logger.isDebugEnabled) {
            loggging(
                requestUri,
                isException,
                session,
                call.request
            )
        }
        logger.debug("### Route completed: ${call.route} : ${call.callId} ]")
    }

    routing {
        authenticate("api") {
            user(userService)
            tenant(tenantService)
            terminal(terminalService, counterService)
        }
        counter(counterService, terminalService)
        admin(adminService)
        sso(ssoService)
        test()

    }

    logger.debug("Application start... OK")
}
