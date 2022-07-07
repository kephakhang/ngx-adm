package com.youngplussoft.admin.server.route

import com.youngplussoft.admin.exception.YpsBadRequestException
import com.youngplussoft.admin.exception.YpsForbiddenException
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.own.dto.UserDto
import com.youngplussoft.admin.server.jpa.own.dto.UserUpdateDto
import com.youngplussoft.admin.server.jpa.own.enum.UserLevel
import com.youngplussoft.admin.server.service.UserService
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.user(userService: UserService) {

    get("/api/v1/user") {
        aop(call, UserLevel.FACTORY.no) {
            call.respond(HttpStatusCode.OK, it!!)
        }
    }

    get("/api/v1/user/list") {
        aop(call, UserLevel.YoungPlusSoft.no) {
            val pageno = call.request.queryParameters["pageno"]?.toLong() ?: 1L
            val size = call.request.queryParameters["size"]?.toLong() ?: 300L
            val list: List<UserDto> = userService.getListByLevel(it!!.level, pageno, size).map{it.toDto()}.toList()
            logger.debug("[GET] /api/v1/user/list : ${Env.gson.toJson(list)}")
            call.respond(HttpStatusCode.OK, list)
        }
    }

    get("/api/v1/user/test") {
        call.respond(HttpStatusCode.OK, mapOf("hello" to "user's world"))
    }

    post("/api/v1/user") {
        aop(call, UserLevel.YoungPlusSoft.no) {
            val userDto: UserDto = call.receive<UserDto>()
            if (userDto.level < it!!.level) {
                val dto = userService.save(userDto.toEntity()).toDto()
                logger.debug("[POST] /api/v1/user : ${Env.gson.toJson(dto)}")
                call.respond(dto)
            } else {
                throw YpsForbiddenException(it!!, call)
            }
        }
    }

    put("/api/v1/user") {
        aop(call, UserLevel.FACTORY.no) {
            val userUpdateDto: UserUpdateDto = call.receive<UserUpdateDto>()
            if (it!!.id.equals(userUpdateDto.id) || (UserLevel.YoungPlusSoft.no <= it!!.level && userUpdateDto.level <= it!!.level)) {
                val member = userService.updateUser(userUpdateDto)?: throw YpsBadRequestException(null, "cannot update user ${userUpdateDto.email}")
                call.respond(member.toDto())
            } else {
                throw YpsForbiddenException(it!!, call)
            }
        }
    }

    delete("/api/v1/user") {
        var id: String = ""
        aop(call, UserLevel.ADMIN.no) {
            val id = call.request.queryParameters["id"]?.toString()?: throw YpsBadRequestException(null, "User Id not found")
            val dto = userService.delete(id).toDto()
            logger.debug("[DELETE] /api/v1/user : ${Env.gson.toJson(dto)}")
            call.respond(dto)
        }
    }
}
