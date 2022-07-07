package com.youngplussoft.admin.server.jpa.own.dto

import com.youngplussoft.admin.server.jpa.own.entity.BoardFile
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls


data class BoardFileDto (

    val id: String? = null,

    val tenantId: String? = "",

    // ToDo : add fields....

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val regDatetime: String? = null,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val modDatetime: String? = null

): BasetDto<BoardFile>() {

    override fun toEntity(): BoardFile {
        val dto = this
        val entity = BoardFile(
            // ToDo add fields....
        )

        return entity
    }
}
