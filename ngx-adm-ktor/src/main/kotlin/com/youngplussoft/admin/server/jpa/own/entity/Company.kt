package com.youngplussoft.admin.server.jpa.own.entity

import com.youngplussoft.admin.common.DateUtil
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.common.entity.BaseEntity
import com.youngplussoft.admin.server.jpa.own.dto.CallDto
import com.youngplussoft.admin.server.jpa.own.dto.CompanyDto
import com.youngplussoft.admin.server.model.PostBody
import java.time.LocalDate
import javax.persistence.*

@Entity(name = "Company")
@Table(name = Env.tablePrefix + "company")
@JsonInclude(JsonInclude.Include.NON_NULL)
open class Company(

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ag_id", insertable = false, updatable = false)
  var agent: Agent? = null,

  /**
   * 가입회사 대행사 고유 ID
   */
//  @ApiModelProperty("가입회사 대행사 고유 ID")
  @Column(name = "ag_id")
  var agId: String? = null,

  /**
   * 가입회사 등록 member 고유 ID
   */
//  @ApiModelProperty("가입회사 등록 member 고유 ID")
  @Column(name = "mb_id")
  var mbId: String? = null,

  /**
   * 가입회사 코드
   */
//  @ApiModelProperty("가입회사 코드")
  @Column(name = "co_code", nullable = false)
  var coCode: String? = null,

  /**
   * 가입회사명
   */
//  @ApiModelProperty("가입회사명")
  @Column(name = "co_name", nullable = false)
  var coName: String? = null,

  /**
   * 0:개인, 1:법인, 2:협동조합...
   */
//  @ApiModelProperty("0:개인, 1:법인, 2:협동조합...")
  @Column(name = "co_type", nullable = false)
  var coType: Int = 0,

  /**
   * 가입회사 사업자번호
   */
//  @ApiModelProperty("가입회사 사업자번호")
  @Column(name = "co_reg_no", nullable = false)
  var coRegNo: String? = null,

  @Column(name = "co_contract_start")
  //@Contextual
  var coContractStart: LocalDate? = null,

  @Column(name = "co_contract_end")
  //@Contextual
  var coContractEnd: LocalDate? = null,

  /**
   * 상태 flag(-3: blocked, -2: withdrawal, -1: dormant, 0:wait, 1:company-otp-ceritified)
   */
  @Column(name = "co_status", nullable = false)
//  @ApiModelProperty("상태 flag(-3: blocked, -2: withdrawal, -1: dormant, 0:wait, 1:company-otp-ceritified)")
  var coStatus: Int = 0,

  /**
   * 상세정보 JSON
   */
  @Column(name = "co_prop")
//  @ApiModelProperty("상세정보 JSON")
  var coProp: String? = null,

  @Column(name = "co_phone")
  var coPhone: String? = null,

  @Column(name = "co_fax")
  var coFax: String? = null,

  @Column(name = "co_zip")
  var coZip: String? = null,

  @Column(name = "co_addr1")
  var coAddr1: String? = null,

  @Column(name = "co_addr2")
  var coAddr2: String? = null,

  @Column(name = "co_latitude")
  var coLatitude: Double? = null,

  @Column(name = "co_longitude")
  var coLongitude: Double? = null,

  /**
   * 대행사 홈페이지
   */
  @Column(name = "co_homepage")
//  @ApiModelProperty("대행사 홈페이지")
  var coHomepage: String? = null,

//  @OneToMany(fetch = FetchType.LAZY, mappedBy = "company", cascade = [CascadeType.ALL])
//  var companyMemberList: MutableList<CompanyMember> = mutableListOf()

) : BaseEntity<CompanyDto>() {

  override fun toDto(): CompanyDto {
    return CompanyDto(
      id = id,
      tenantId = teId,
      // ToDo add fields
      regDatetime =  DateUtil.localDatetimeToStr(createdAt),
      modDatetime = DateUtil.localDatetimeToStr(updatedAt),
      delDatetime = DateUtil.localDatetimeToStr(deletedAt)
    )
  }
  fun copy(agent: Agent) {
    this.agent = agent
  }

//  fun addCompanyMember(companyMember: CompanyMember) {
//    companyMember.copy(company = this)
//    companyMemberList.add(companyMember)
//  }

}
