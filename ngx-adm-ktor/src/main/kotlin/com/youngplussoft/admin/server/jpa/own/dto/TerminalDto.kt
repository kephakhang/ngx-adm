package com.youngplussoft.admin.server.jpa.own.dto

import com.youngplussoft.admin.common.DateUtil
import com.youngplussoft.admin.server.jpa.own.entity.Tenant
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls
import java.time.LocalDateTime
import javax.swing.plaf.multi.MultiTableUI
import com.youngplussoft.admin.server.jpa.own.entity.Terminal as Terminal


data class TerminalDto(

    val id: String? = "",

    val tenantId: String? = "",

    val version: Int = 3, // v3

    val status: Int = 0,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val ip: String? = null,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val regDatetime: String? = null,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val modDatetime: String? = null,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val delDatetime: String? = null,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val counterList: MutableList<CounterDto> = listOf<CounterDto>().toMutableList()

    ): BasetDto<Terminal>()   {

    override fun toEntity(): Terminal {
        val dto = this
        val tenant = Terminal()
        return tenant.apply {
            id = dto.id
            teId = dto.tenantId
            trVersion = dto.version
            trStatus = dto.status
            trIp = dto.ip
            createdAt = null
            updatedAt = null
            deletedAt = null
            counterList = dto.counterList.map{it -> it.toEntity()}.toMutableList()
        }
    }
}
