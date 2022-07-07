package com.youngplussoft.admin.server.route

import com.youngplussoft.admin.exception.YpsBadRequestException
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.own.dto.TerminalDto
import com.youngplussoft.admin.server.jpa.own.enum.UserLevel
import com.youngplussoft.admin.server.service.CounterService
import com.youngplussoft.admin.server.service.TerminalService
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.terminal(terminalService: TerminalService, counterService: CounterService) {

    get("/api/v1/terminal") {
        aop(call, UserLevel.YoungPlusSoft.no) {
            val terminalId = call.request.queryParameters["id"]?.toString()?: throw YpsBadRequestException(null, "Terminal Id not found")
            call.respond(HttpStatusCode.OK, terminalService.get(terminalId))
        }
    }

    get("/api/v1/terminal/list") {
        aop(call, UserLevel.YoungPlusSoft.no) {
            val tenantId = call.request.queryParameters["tenantId"]?.toString()
            val list = terminalService.getListByTeId(tenantId)
            val dtoList = list.map{it.toDto()}.toList()
            //logger.debug("[GET] /api/v1/terminal/list : ${Env.gson.toJson(dtoList)}")
            call.respond(HttpStatusCode.OK, dtoList)
        }
    }

    get("/api/v1/terminal/hello") {
        call.respond(HttpStatusCode.OK, mapOf("hello" to "terminal's world"))
    }

    post("/api/v1/terminal") {
        aop(call, UserLevel.YoungPlusSoft.no) {
            val terminalDto: TerminalDto = call.receive<TerminalDto>()
            val dto = terminalService.save(terminalDto.toEntity(), false).toDto()
            logger.debug("[POST] /api/v1/terminal : ${Env.gson.toJson(dto)}")
            call.respond(dto)
        }
    }

    put("/api/v1/terminal") {
        aop(call, UserLevel.YoungPlusSoft.no) {
            val terminalDto: TerminalDto = call.receive<TerminalDto>()
            val dto = terminalService.update(terminalDto.toEntity()).toDto()
            logger.debug("[PUT] /api/v1/terminal : ${Env.gson.toJson(dto)}")
            call.respond(dto)
        }
    }

    delete("/api/v1/terminal") {
        aop(call, UserLevel.YoungPlusSoft.no) {
            val id = call.request.queryParameters["id"]?.toString()?: throw YpsBadRequestException(null, "Terminal Id not found")
            val dto = terminalService.delete(id).toDto()
            logger.debug("[DELETE] /api/v1/terminal : ${Env.gson.toJson(dto)}")
            call.respond(HttpStatusCode.OK, dto)
        }
    }

}

