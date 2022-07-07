package com.youngplussoft.admin.server.jpa.own.dto


import com.auth0.jwt.interfaces.Payload
import com.youngplussoft.admin.common.DateUtil
import com.youngplussoft.admin.server.jpa.own.entity.Admin
import com.youngplussoft.admin.server.jpa.own.entity.Member
import com.fasterxml.jackson.annotation.*
import io.ktor.server.auth.*
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserUpdateDto(

    val id: String = "",

    val tenantId: String? = "",

    val email: String = "",

    val mobile: String = "",

    val name: String = "",

    val level: Int = 1,


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val regDatetime: String? = null,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val modDatetime: String? = null
)

class UserUpdateWrapper(val user: UserUpdateDto)
