package com.youngplussoft.admin.server.route

import com.youngplussoft.admin.exception.YpsBadRequestException
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.own.dto.CounterDto
import com.youngplussoft.admin.server.jpa.own.dto.TenantDto
import com.youngplussoft.admin.server.jpa.own.enum.UserLevel
import com.youngplussoft.admin.server.service.TenantService
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.tenant(tenantService: TenantService) {

    get("/api/v1/tenant") {
        aop(call, UserLevel.YoungPlusSoft.no) {
            val tenantId = call.request.queryParameters["id"]?.toString()?: throw YpsBadRequestException(null, "request parameter is missed : Tenant Id not found")
            call.respond(tenantService.get(tenantId).toDto())
        }
    }

    get("/api/v1/tenant/list") {
        aop(call, UserLevel.FACTORY.no) {
//            val pageno = call.request.queryParameters["pageno"]?.toLong() ?: 1L
//            val size = call.request.queryParameters["size"]?.toLong() ?: 10L
            val list  = tenantService.getList().map{it.toDto()}.toList()
            //logger.debug("[GET] /api/v1/tenant/list : ${Env.gson.toJson(list)}")
            call.respond(list)
        }
    }

    get("/api/v1/tenant/test") {
        call.respond(HttpStatusCode.OK, mapOf("hello" to "tenant's world"))
    }

    post("/api/v1/tenant") {
        aop(call, UserLevel.YoungPlusSoft.no) {
            val tenantDto: TenantDto = call.receive<TenantDto>()
            val dto = tenantService.save(tenantDto.toEntity(), false).toDto()
            logger.debug("[POST] /api/v1/tenant : ${Env.gson.toJson(dto)}")
            call.respond(dto)
        }
    }

    put("/api/v1/tenant") {
        aop(call, UserLevel.YoungPlusSoft.no) {
            val tenantDto: TenantDto = call.receive<TenantDto>()
            val dto = tenantService.update(tenantDto.toEntity()).toDto()
            logger.debug("[PUT] /api/v1/tenant : ${Env.gson.toJson(dto)}")
            call.respond(dto)
        }
    }

    delete("/api/v1/tenant") {
        aop(call, UserLevel.YoungPlusSoft.no) {
            val id = call.request.queryParameters["id"]?.toString()?: throw YpsBadRequestException(null, "Tenant Id not found")
            val dto = tenantService.delete(id).toDto()
            logger.debug("[DELETE] /api/v1/tenant : ${Env.gson.toJson(dto)}")
            call.respond(dto)
        }
    }
}
