package com.youngplussoft.admin.server.jpa.own.dto

import com.youngplussoft.admin.common.DateUtil
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.own.entity.Call
import com.youngplussoft.admin.server.model.PostBody
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import com.youngplussoft.admin.server.jpa.own.entity.Tenant as Tenant


data class CallDto (

    val id: String? = null,

    val tenantId: String? = "",

    val requestId: String = "",

    val uri: String = "",

    val requestUrl: String = "",

    val method: String = "",

    val requestBody: PostBody? = null,

    val responseBody: PostBody? = null,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val regDatetime: String? = null,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val modDatetime: String? = null

    ): BasetDto<Call>() {

    override fun toEntity(): Call {
        val dto = this
        val call = Call(
                caRequestId = dto.requestId,
                caUri = dto.uri,
                caMethod = dto.method,
                caRequestBody = Env.gson.toJson(dto.requestBody!!),
                caResponseBody = Env.gson.toJson(dto.responseBody!!)
        )
        call.id = dto.id
        call.teId = dto.tenantId
        call.regDatetime = null
        call.modDatetime = null

        return call
    }
}
