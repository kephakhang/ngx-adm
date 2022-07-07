package com.youngplussoft.admin.server.route

import com.youngplussoft.admin.server.jpa.own.dto.LoginDto
import com.youngplussoft.admin.server.jpa.own.dto.SignupDto
import com.youngplussoft.admin.server.jpa.own.enum.UserLevel
import com.youngplussoft.admin.server.service.AdminService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.jvm.Throws


@Throws(Exception::class)
fun Route.admin(adminService: AdminService) {

    post("/sso/admin/login") {
        aop(call, UserLevel.ADMIN.no) {
            val login = call.receive<LoginDto>()
            val user = adminService.login(login)
            call.respond(user)
        }
    }

    post("/sso/admin/signup") {
        aop(call, UserLevel.ADMIN.no) {
            val signup = call.receive<SignupDto>()
            val user = adminService.register(signup)
            call.respond(user)
        }
    }
}
