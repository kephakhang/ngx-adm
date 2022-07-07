package com.youngplussoft.admin.server.route

import com.youngplussoft.admin.common.KeyGenerator
import com.youngplussoft.admin.exception.*
import com.youngplussoft.admin.extensions.stackTraceString
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.own.dto.Response
import com.youngplussoft.admin.server.jpa.own.dto.UserDto
import com.youngplussoft.admin.server.jpa.own.entity.Call
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mu.KotlinLogging

val logger = KotlinLogging.logger {}
const val CRLF = "\n"
const val REQUEST_PREFIX = "Request: "
const val RESPONSE_PREFIX = "Response: "

private fun isHtml(requestUri: String): Boolean {
    return try {
        if ("/(.+\\.(ico|js|css|html|jpg|jpeg|png|gif|svg|mp3|mp4))".toRegex()
                .containsMatchIn(requestUri)
        ) true else false
    } catch (ex: Exception) {
        false
    }
}

private fun isMultipart(request: ApplicationRequest): Boolean {
    return (request.contentType().toString().startsWith("multipart/form-data"))
}

private fun isFormSubmit(request: ApplicationRequest): Boolean {
    return (request.contentType().toString().startsWith(
        "application/x-www-form-urlencoded"
    ))
}

fun loggging(
//    startTime: Long,
    requestUri: String,
    isException: Boolean,
    session: UserDto?,
    req: ApplicationRequest,
    response: Any? = null
) {
    val msg = StringBuilder()
    if (!isException) {

        msg.append(CRLF).append("################################")
            .append(CRLF).append(REQUEST_PREFIX)
            .append(requestUri).append(":")

    } else {
        msg.append(CRLF).append("###Exception####################")
            .append(CRLF).append(REQUEST_PREFIX)
            .append(requestUri)
    }
    if (req.queryParameters != null
        && !req.queryParameters.isEmpty()
    ) msg.append('?').append(req.queryString())
    msg.append(" ").append(req)
    if (session != null) {
        msg.append(CRLF).append("SESSION ID: ").append(session.id)
        session.token?.let {
            msg.append(CRLF).append("Authentication: ").append(session.token.id).append(CRLF)
        }
    }

    var ip: String? = req.header("X-FORWARDED-FOR")
    //if (ip == null) ip = req()

    ip?.let {
        msg.append(CRLF).append("Remote-IP: ")
            .append(ip).append(CRLF)
    }

    msg.append("Content-Type: ").append(req.contentType().toString())

    if (!isMultipart(req)) {
        msg.append(CRLF).append("Request Body: ").append(requestUri).append(":").append(CRLF)
        if (isFormSubmit(req)) {
            var firstParam = true
            val entries = req.queryParameters.entries()
            entries.forEach {
                val pn = it.key
                val pv = it.value
                for (l in pv.indices) {
                    if (firstParam) {
                        msg.append(pn + "=" + pv[l])
                        firstParam = false
                    } else {
                        msg.append("&").append(pn).append("=")
                            .append(pv[l])
                    }
                }
            }
        } else {
            msg.append(req.toString())
        }
    }
//    msg.append(CRLF).append("Elapsed Time: ")
//        .append(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime))
//        .append(" msec")


    if (response != null && response is Response) {
        msg.append(CRLF).append("-------------------------------")
            .append(CRLF).append(RESPONSE_PREFIX)
        msg.append(CRLF).append(Env.gson.toJson(response))
    }

    msg.append(CRLF).append("################################")
    if (isException) {
        logger.error(
            msg.toString().replace("\\t".toRegex(), "\t").replace("\\n".toRegex(), "\n")
        )
    } else {
        logger.debug(
            if (msg.length > 20480) msg.substring(0, 20480)
                    + "..... (more data. len:" + msg.length + ")" else msg
                .toString().replace("\\t".toRegex(), "\t").replace("\\n".toRegex(), "\n")
        )
    }
}

inline suspend fun aop(call: ApplicationCall, level: Int? = null, body: (session: UserDto?) -> Unit): UserDto? {
    val CRLF = "\n"
    val REQUEST_PREFIX = "Request: "
    val RESPONSE_PREFIX = "Response: "
    var isException = false
    var session: UserDto? = null
    val requestUri: String = call.request.uri
    val method: String = call.request.httpMethod.value
    val tid: String = KeyGenerator.generateKey()

    val startTime = System.nanoTime()

    try {
        if (level !== null) {
            val session = call.request.call.authentication.principal<UserDto>() ?: throw YpsSessionNotFoundException(null, "User Not Found")
            if (session.level >= level) {
                body(session)
            } else {
                throw YpsForbiddenException(session, call)
            }
        } else {
            body(null)
        }

    } catch (ex: YpsException) {
        logger.error("routing error : ${ex.stackTraceString}")
        isException = true

        val err = Env.error(ex)
        val errStatus = if (ex.httpStatus === null) err.status else ex.httpStatus
        val response = Response(err as Any, tid, requestUri, method.uppercase())
        //CoroutineScope(Dispatchers.Main).launch {
        // background coroutine like thread
        call.respond(HttpStatusCode(errStatus, err.description), response)
        //}
    } catch (t: Throwable) {
        logger.error("routing error : ${t.stackTraceString}")
        isException = true
        val err = Env.error(ErrorCode.E00000)
        err.description = t.localizedMessage
        val response = Response(err as Any, tid, requestUri, method.uppercase())
        //CoroutineScope(Dispatchers.Main).launch {
        // background coroutine like thread
        call.respond(HttpStatusCode(err.status, err.description), response)
        //}

    } finally {
        if (logger.isDebugEnabled) {
            loggging(
                requestUri,
                isException,
                session,
                call.request,
                call.response
            )
        }
        return session
    }
}


