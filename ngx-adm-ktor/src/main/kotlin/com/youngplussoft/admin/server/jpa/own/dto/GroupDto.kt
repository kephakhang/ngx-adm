package com.youngplussoft.admin.server.jpa.own.dto

import com.youngplussoft.admin.common.DateUtil
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.own.entity.Board
import com.youngplussoft.admin.server.jpa.own.entity.Call
import com.youngplussoft.admin.server.jpa.own.entity.Group
import com.youngplussoft.admin.server.model.PostBody
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls
import java.time.LocalDateTime
import com.youngplussoft.admin.server.jpa.own.entity.Tenant as Tenant


data class GroupDto (

    val id: String? = null,

    val tenantId: String? = "",

    // ToDo : add fields....

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val regDatetime: String? = null,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val modDatetime: String? = null

    ): BasetDto<Group>() {

    override fun toEntity(): Group {
        val dto = this
        val entity = Group(
            // ToDo add fields....
        )
        entity.id = dto.id
        entity.teId = dto.tenantId
        entity.regDatetime = null
        entity.modDatetime = null

        return entity
    }
}
