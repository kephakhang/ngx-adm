package com.youngplussoft.admin.server.jpa.own.entity

import com.youngplussoft.admin.common.DateUtil
import com.fasterxml.jackson.annotation.JsonInclude
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.common.entity.BaseEntity
import com.youngplussoft.admin.server.jpa.own.dto.UserDto
//import io.swagger.annotations.ApiModel
//import io.swagger.annotations.ApiModelProperty
import javax.persistence.*

@Entity(name = "Member")
//@ApiModel("사용자")
@Table(name = Env.tablePrefix + "member")
@JsonInclude(JsonInclude.Include.NON_NULL)
open class Member(

  @Column(name = "mb_jwt")
  var mbJwt: String? = null,

  @Column(name = "mb_email_hash",  nullable = false)
  var mbEmailHash: String = "",

  @Column(name = "mb_mobile_hash",  nullable = false)
  var mbMobileHash: String = "",

  @Column(name = "mb_password", nullable = false)
  var mbPassword: String = "",

  @Column(name = "mb_name", nullable = false)
  var mbName: String = "",

//  @ApiModelProperty("member level flat (0:none, 1:guest, 10:company, 11:company user, 30:agent, 31:agent user, 1000:admin)")
  @Column(name = "mb_level", nullable = false)
  var mbLevel: Int = 1,

  @Column(name = "mb_point", nullable = false)
  var mbPoint: Long = 0L,


//  @ApiModelProperty("member email or mobile certification status falg(-2: blocked, -1: dormant, 0:wait for certification, 1:email-only, 2:mobile-only, 3:both")
  @Column(name = "mb_status", nullable = false)
  var mbStatus: Int = 0,

  @OneToOne(fetch = FetchType.LAZY, mappedBy = "member", cascade = [CascadeType.ALL])
  var detail: MemberDetail? = null

) : BaseEntity<UserDto>() {

  override fun toDto(): UserDto {
    return UserDto(
      id = id!!,
      tenantId = teId!!,
      jwt = mbJwt,
      password = mbPassword,
      name = mbName,
      level = mbLevel,
      status = mbStatus,
      point = mbPoint,
      regDatetime = DateUtil.localDatetimeToStr(regDatetime),
      modDatetime = DateUtil.localDatetimeToStr(modDatetime),
      detail = detail?.toDto()
    )
  }
}
