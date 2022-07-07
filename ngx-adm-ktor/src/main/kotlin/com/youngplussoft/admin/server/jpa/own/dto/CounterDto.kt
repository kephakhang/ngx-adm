package com.youngplussoft.admin.server.jpa.own.dto

import com.youngplussoft.admin.common.DateUtil
import com.youngplussoft.admin.server.jpa.own.entity.Call
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls
import java.time.LocalDateTime
import com.youngplussoft.admin.server.jpa.own.entity.Counter as Counter


data class CounterDto(

    val id: String? = "",

    val tenantId: String? = "",

    val terminalId: String? = "",

    val version: Int = 3, // v3

    val status: Int = 0, // 0:available, 1:installed

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val regDatetime: String? = null,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val modDatetime: String? = null

    ): BasetDto<Counter>() {

    override fun toEntity(): Counter {
        val dto = this
        val tenant = Counter()
        return tenant.apply {
            id = dto.id
            teId = dto.tenantId
            trId = dto.terminalId
            coVersion = dto.version
            coStatus = dto.status
            regDatetime = null
            modDatetime = null
        }
    }
}
