package com.youngplussoft.admin.server.jpa.own.dto

import com.youngplussoft.admin.common.DateUtil
import com.youngplussoft.admin.server.jpa.own.entity.Counter
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls
import java.time.LocalDateTime
import com.youngplussoft.admin.server.jpa.own.entity.Tenant as Tenant


data class TenantDto(

    val id: String? = "",

    val name: String = "",

    val type: Int = 0, //0:OWN, 1:OEM, 2:MoldMaker, 3:Supply

    val description: String = "",

    val countryCode: String = "",

    val hostUrl: String = "",

    val prefix: String = "",

    val hostname: String = "",

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val regDatetime: String? = null,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val modDatetime: String? = null

    ): BasetDto<Tenant>()  {

    override fun toEntity(): Tenant {
        val dto = this
        val tenant = Tenant()
        return tenant.apply {
            id = dto.id
            name = dto.name
            type = dto.type
            description = dto.description
            jdbcHost = null
            jdbcUser = null
            jdbcPass = null
            countryId = dto.countryCode
            hostUrl = dto.hostUrl
            enable = true
            prefix = dto.prefix
            hostname = dto.hostname
            regDatetime = null
            modDatetime = null
        }
    }
}
