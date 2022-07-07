package com.youngplussoft.admin.server.jpa.own.entity

import com.youngplussoft.admin.common.DateUtil
import com.fasterxml.jackson.annotation.JsonInclude
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.common.entity.BaseEntity
import com.youngplussoft.admin.server.jpa.own.dto.UserDto
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

/**
 * 관리자
 */
@Entity(name = "Admin")
//@ApiModel("관리자")
@Table(name = Env.tablePrefix + "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
open class Admin(

  /**
   * admin sha256(email)
   */
 // @ApiModelProperty("admin sha256(email)")
  @Column(name = "am_email_hash",  nullable = false)
  var amEmailHash: String = "",

  /**
   * admin password
   */
  //@ApiModelProperty("admin password")
  @Column(name = "am_password", nullable = false)
  var amPassword: String = "",

  /**
   * admin name
   */
  //@ApiModelProperty("admin name")
  @Column(name = "am_name", nullable = false)
  var amName: String = "",

  /**
   * admin mobile
   */
  //@ApiModelProperty("admin mobile")
  @Column(name = "am_mobile", nullable = false)
  var amMobile: String = "",

  /**
   * 사번
   */
  //@ApiModelProperty("사번")
  @Column(name = "am_reg_no")
  var amRegNo: String? = null,

  /**
   * OTP 암호
   */
  //@ApiModelProperty("OTP 암호")
  @Column(name = "am_otp")
  var amOtp: String? = null,

  /**
   * 레벨(1000:관리자)
   */
  //@ApiModelProperty("레벨(1000:관리자)")
  @Column(name = "am_level", nullable = false)
  var amLevel: Int = 1000,

  /**
   * 상태 flag(-3: blocked, -2: withdrawal, -1: dormant, 0:wait, 1:ceritified)
   */
  //@ApiModelProperty("상태 flag(-3: blocked, -2: withdrawal, -1: dormant, 0:wait, 1:ceritified)")
  @Column(name = "am_status", nullable = false)
  var amStatus: Int = 0,

  /**
   * 메모
   */
  //@ApiModelProperty("메모")
  @Column(name = "am_memo")
  var amMemo: String? = null,

  /**
   * 등록자 admin ID
   */
  //@ApiModelProperty("등록자 admin ID")
  @Column(name = "am_registrant_id")
  var amRegistrantId: String? = null,

  /**
   * 수정자 admin ID
   */
  //@ApiModelProperty("수정자 admin ID")
  @Column(name = "am_modifier_id")
  var amModifierId: String? = null,

  /**
   * 관리자 상세정보
   */
  @Column(name = "am_detail")
  //@ApiModelProperty("관리자 상세정보")
  var amDetail: String = "Bory Inc."

) : BaseEntity<UserDto>() {

  override fun toDto(): UserDto {
    return UserDto(
      id = id!!,
      tenantId = teId!!,
      password = amPassword,
      name = amName,
      level = amLevel,
      status = amStatus,
      regDatetime = DateUtil.localDatetimeToStr(regDatetime),
      modDatetime = DateUtil.localDatetimeToStr(modDatetime)
    )
  }
}
