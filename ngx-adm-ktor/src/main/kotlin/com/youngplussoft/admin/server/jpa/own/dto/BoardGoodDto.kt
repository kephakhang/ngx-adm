package com.youngplussoft.admin.server.jpa.own.dto

import com.youngplussoft.admin.server.jpa.own.entity.BoardGood
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls


data class BoardGoodDto (

    val id: String? = null,

    val tenantId: String? = "",

    // ToDo : add fields....

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val regDatetime: String? = null,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val modDatetime: String? = null,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val delDatetime: String? = null

): BasetDto<BoardGood>() {

    override fun toEntity(): BoardGood {
        val dto = this
        val entity = BoardGood(
            // ToDo add fields....
        )

        return entity
    }
}
