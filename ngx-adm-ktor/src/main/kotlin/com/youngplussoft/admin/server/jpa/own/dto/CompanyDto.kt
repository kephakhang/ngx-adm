package com.youngplussoft.admin.server.jpa.own.dto

import com.youngplussoft.admin.common.DateUtil
import com.youngplussoft.admin.server.jpa.own.entity.Company
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls


data class CompanyDto (

    val id: String? = null,

    val tenantId: String? = "",

// ToDo add fields

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val regDatetime: String? = null,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val modDatetime: String? = null,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val delDatetime: String? = null

    ): BasetDto<Company>() {

    override fun toEntity(): Company {
        val dto = this
        val entity = Company(
        )
        entity.id = dto.id
        entity.teId = dto.tenantId
        entity.createdAt = null
        entity.updatedAt = null
        entity.deletedAt = null

        return entity
    }
}
