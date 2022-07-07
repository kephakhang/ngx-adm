package com.youngplussoft.admin.server.route

import com.youngplussoft.admin.exception.YpsException
import com.youngplussoft.admin.exception.ErrorCode
import com.youngplussoft.admin.exception.YpsSessionNotFoundException
import com.youngplussoft.admin.server.jpa.own.dto.UserDto
import com.youngplussoft.admin.server.service.UserService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.http.*
import io.ktor.server.locations.get
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.test() {

    get("/hello") {
        call.respond(mapOf("hello" to "world"))
    }
}
