package com.youngplussoft.admin.server.jpa.own.dto


import com.auth0.jwt.interfaces.Payload
import com.youngplussoft.admin.common.DateUtil
import com.youngplussoft.admin.server.jpa.own.entity.Admin
import com.youngplussoft.admin.server.jpa.own.entity.Member
import com.fasterxml.jackson.annotation.*
import io.ktor.server.auth.*
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserDto(
    @JsonIgnore
    val token: Payload? = null,  //Json Web Token after authentication

    val id: String? = null,

    val tenantId: String? = "",

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    var jwt: String? = null,

    @JsonIgnore
    val emailHash: String = "",

    @JsonIgnore
    val mobileHash: String = "",

    @JsonIgnore
    val password: String = "",

    val name: String = "",

    val level: Int = 1,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val point: Long = 0,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val status: Int = 0,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val regDatetime: String? = null,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val modDatetime: String? = null,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val detail: UserDetailDto? = null
) : Principal, BasetDto<Member>()  {

    @get:JsonIgnore
    val profile
        get() = ProfileDto(name, detail?.gender, detail?.greeting, detail?.image, false)

    override fun toEntity(): Member {
        val dto = this
        val member = Member()
        return member.apply {
            id = id
            teId = tenantId
            mbJwt = jwt
            mbEmailHash = emailHash
            mbMobileHash = mobileHash
            mbPassword = password
            mbName = name
            mbLevel = level
            mbStatus = status
            mbPoint = point
            regDatetime = null
            modDatetime = null
            detail = dto.detail?.toEntity()
        }
    }

    fun toAdmin(): Admin {
        val dto = this
        val admin = Admin()
        return admin.apply {
            id = dto.id
            teId = dto.tenantId
            amEmailHash = dto.emailHash
            amPassword = dto.password
            amName = dto.name
            amLevel = dto.level
            amStatus = dto.status
            regDatetime = null
            modDatetime = null
        }
    }
}

class UserWrapper(val user: UserDto)
