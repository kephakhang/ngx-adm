package com.youngplussoft.admin.server.route

import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.own.dto.LoginDto
import com.youngplussoft.admin.server.jpa.own.dto.SignupDto
import com.youngplussoft.admin.server.jpa.own.dto.ConfirmDto
import com.youngplussoft.admin.server.model.NOK
import com.youngplussoft.admin.server.model.OK
import com.youngplussoft.admin.server.service.SsoService
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.jvm.Throws

@Throws(Exception::class)
fun Route.sso(ssoService: SsoService) {

    get("/sso/hello") {
        call.respond(mapOf("/api/sso/hello" to "world"))
    }

    post("/sso/login") {
        aop(call, null) {
            val login: LoginDto = call.receive<LoginDto>()
            val user = ssoService.login(login)
            call.respond(user)
        }
    }

    get("/sso/check") {
        aop(call, null) {
            val loginId: String = call.parameters["loginId"].toString()
            if (ssoService.checkAvailable(loginId)) {
                call.respond(OK)
            } else {
                call.respond(HttpStatusCode.Found, NOK)
            }
        }
    }

    post("/sso/signup") {
        aop(call, null) {
            val signup = call.receive<SignupDto>()
            logger.debug("/sso/signup", Env.gson.toJson(signup))
            val user = ssoService.register(signup)
            call.respond(user)
        }
    }

    get("/sso/confirm/email") {
        aop(call, null) {
            val email: ConfirmDto = call.receive<ConfirmDto>()
            val uid = email.uid
            val confirm = email.confirm
            val ret: String? = ssoService.confirmEmail(uid, confirm)
            if (ret == null) {
                call.respondRedirect(Env.confirmSuccessUrl)
            } else {
                call.respondRedirect(Env.confirmFailureUrl)
            }
        }
    }
}

