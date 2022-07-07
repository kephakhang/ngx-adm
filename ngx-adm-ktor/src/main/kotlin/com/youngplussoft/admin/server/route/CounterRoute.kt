package com.youngplussoft.admin.server.route

import com.youngplussoft.admin.exception.YpsBadRequestException
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.own.dto.CounterDto
import com.youngplussoft.admin.server.jpa.own.entity.Terminal
import com.youngplussoft.admin.server.service.CounterService
import com.youngplussoft.admin.server.service.TerminalService
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.counter(counterService: CounterService, terminalService: TerminalService) {

    get("/api/v1/counter") {
        aop(call, null) {
            val id = call.request.queryParameters["id"]?.toString()?: throw YpsBadRequestException(null, "request parameter is missed : Counter Id not found")
            call.respond(counterService.get(id).toDto())
        }
    }

    get("/api/v1/counter/list") {
        aop(call, null) {
            val tenantId = call.request.queryParameters["tenantId"]?.toString()
            val terminalId = call.request.queryParameters["terminalId"]?.toString()
            val list: List<CounterDto> = counterService.getListByTeIdAndTrId(tenantId, terminalId).map{it.toDto()}.toList()
            //logger.debug("[GET] /api/v1/counter/list : ${Env.gson.toJson(list)}")
            call.respond(list)
        }
    }

    get("/api/v1/counter/hello") {
        call.respond(HttpStatusCode.OK, mapOf("hello" to "counter's world"))
    }


    post("/api/v1/counter") {
        aop(call, null) {
            val counterDto: CounterDto = call.receive<CounterDto>()
            val dto = counterService.save(counterDto.toEntity(), false).toDto()
            logger.debug("[POST] /api/v1/counter : ${Env.gson.toJson(dto)}")
            call.respond(dto)
        }
    }

    put("/api/v1/counter") {
        aop(call, null) {
            val counterDto: CounterDto = call.receive<CounterDto>()
            val terminal: Terminal = terminalService.get(counterDto.terminalId!!)?: throw YpsBadRequestException(null, "The terminal of counter doesn't exist in the db table : ${counterDto.terminalId}")
            if (!terminal.teId.equals(counterDto.tenantId)) {
                throw YpsBadRequestException(null, "The terminal of counter is not in the tenant : ${counterDto.tenantId} =/= ${counterDto.terminalId}")
            }
            val dto = counterService.update(counterDto.toEntity()).toDto()
            logger.debug("[PUT] /api/v1/counter : ${Env.gson.toJson(dto)}")
            call.respond(dto)
        }
    }

    delete("/api/v1/counter") {
        aop(call, null) {
            val id = call.request.queryParameters["id"]?.toString()?: throw YpsBadRequestException(null, "request parameter is missed : Counter Id not found")
            val dto = counterService.delete(id).toDto()
            logger.debug("[DELETE] /api/v1/counter : ${Env.gson.toJson(dto)}")
            call.respond(dto)
        }
    }
}

