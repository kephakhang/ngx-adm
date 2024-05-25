package com.youngplussoft.admin.server.jpa.own.dto

import com.youngplussoft.admin.server.jpa.own.entity.BoardGood
import com.youngplussoft.admin.server.jpa.own.entity.BoardWriteDetail
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls


data class BoardWriteDetailDto (

    val tenantId: String? = "",

    // ToDo : add fields....

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val regDatetime: String? = null,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val modDatetime: String? = null,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val delDatetime: String? = null

): BasetDto<BoardWriteDetail>() {

    override fun toEntity(): BoardWriteDetail {
        val dto = this
        val entity = BoardWriteDetail(
            // ToDo add fields....
        )

        return entity
    }
}
