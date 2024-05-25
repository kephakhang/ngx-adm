package com.youngplussoft.admin.server.jpa.own.dto

import com.youngplussoft.admin.server.jpa.own.entity.BoardGood
import com.youngplussoft.admin.server.jpa.own.entity.BoardWrite
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls


data class BoardWriteDto (

    val id: String? = null,

    val tenantId: String? = "",

    // ToDo : add fields....

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val regDatetime: String? = null,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val modDatetime: String? = null,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val delDatetime: String? = null

): BasetDto<BoardWrite>() {

    override fun toEntity(): BoardWrite {
        val dto = this
        val entity = BoardWrite(
            // ToDo add fields....
        )

        return entity
    }
}
