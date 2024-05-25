package com.youngplussoft.admin.server.jpa.own.entity


import com.youngplussoft.admin.common.DateUtil
import com.fasterxml.jackson.annotation.JsonInclude
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.common.entity.BaseEntity
import com.youngplussoft.admin.server.jpa.own.dto.UserDto
import javax.persistence.*

/**
 * 대행사
 */
@Entity(name = "Agent")
@Table(name = Env.tablePrefix + "agent")
@JsonInclude(JsonInclude.Include.NON_NULL)
open class Agent(

  /**
   * agent sha256(email)
   */
//  @ApiModelProperty("agent sha256(email)")
  @Column(name = "ag_email_hash",  nullable = false)
  var agEmailHash: String? = null,

  /**
   * agent sha256(email)
   */
//  @ApiModelProperty("agent sha256(email)")
  @Column(name = "ag_mobile_hash",  nullable = false)
  var agMobileHash: String? = null,

  /**
   * agent password
   */
//  @ApiModelProperty("agent password")
  @Column(name = "ag_password", nullable = false)
  var agPassword: String? = null,

  /**
   * OTP 암호
   */
  @Column(name = "ag_otp")
//  @ApiModelProperty("OTP 암호")
  var agOtp: String? = null,

  @Column(name = "ag_code", nullable = false)
  var agCode: String? = null,

  /**
   * 상태 flag(-3: blocked, -2: withdrawal, -1: dormant, 0:wait, 1:agent-otp-ceritified)
   */
  @Column(name = "ag_status", nullable = false)
//  @ApiModelProperty("상태 flag(-3: blocked, -2: withdrawal, -1: dormant, 0:wait, 1:agent-otp-ceritified)")
  var agStatus: Int = 0,

  /**
   * 레벨 flag(100:agent user, 101:agent admin
   */
  @Column(name = "ag_level", nullable = false)
//  @ApiModelProperty("레벨 flag(100:agent user, 101:agent admin)")
  var agLevel: Int = 100,

  @OneToOne(fetch = FetchType.LAZY, mappedBy = "agent", cascade = [CascadeType.ALL])
  var detail: AgentDetail? = null,

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "agent", cascade = [CascadeType.ALL])
  var companyList: MutableList<Company> = mutableListOf()

) : BaseEntity<UserDto>() {

  override fun toDto(): UserDto {
    return UserDto(
      id = id!!,
      tenantId = teId!!,
      password = agPassword!!,
      level = agLevel,
      status = agStatus,
      regDatetime = DateUtil.localDatetimeToStr(createdAt),
      modDatetime = DateUtil.localDatetimeToStr(updatedAt),
      delDatetime = DateUtil.localDatetimeToStr(deletedAt)
    )
  }

  fun addCompany(company: Company) {
    company.agent = this
    companyList.add(company)
  }
}
