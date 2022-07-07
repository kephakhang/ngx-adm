package com.youngplussoft.admin.server.jpa.own.entity

//import kotlinx.serialization.Serializable
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.youngplussoft.admin.server.env.Env
import java.io.Serializable
import java.time.LocalDate
import javax.persistence.*

/**
 * 대행사 상세
 */
@Entity(name = "AgentDetail")
@Table(name = Env.tablePrefix + "agent_detail")
@JsonInclude(JsonInclude.Include.NON_NULL)
open class AgentDetail(

  @JsonIgnore
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ag_id", insertable = false, updatable = false)
  val agent: Agent? = null,

  /**
   * 대행사 고유 ID (join id)
   */
  @Id
//  @ApiModelProperty("대행사 고유 ID (join id)")
  @Column(name = "ag_id")
  val agId: String? = null,

  @Column(name = "ag_mobile", nullable = false)
  val agMobile: String = "",

  @Column(name = "ag_email", nullable = false)
  val agEmail: String = "",

  @Column(name = "ag_contract_start")
  //@Contextual
  val agContractStart: LocalDate? = null,

  @Column(name = "ag_contract_end")
  //@Contextual
  val agContractEnd: LocalDate? = null,

  @Column(name = "ag_phone")
  val agPhone: String? = null,

  @Column(name = "ag_fax")
  val agFax: String? = null,

  @Column(name = "ag_zip")
  val agZip: String? = null,

  @Column(name = "ag_addr1")
  val agAddr1: String? = null,

  @Column(name = "ag_addr2")
  val agAddr2: String? = null,

  @Column(name = "ag_latitude")
  val agLatitude: Double? = null,

  @Column(name = "ag_longitude")
  val agLongitude: Double? = null,

  /**
   * 대행사 홈페이지
   */
  @Column(name = "ag_homepage")
//  @ApiModelProperty("대행사 홈페이지")
  val agHomepage: String? = null,

  /**
   * 과금 부과 기관 사용자 명
   */
  @Column(name = "ag_charge_name")
//  @ApiModelProperty("과금 부과 기관 사용자 명")
  val agChargeName: String? = null,

  /**
   * 과금 부과 기관
   */
//  @ApiModelProperty("과금 부과 기관")
  @Column(name = "ag_charge_org")
  val agChargeOrg: String? = null,

  /**
   * 과금 부과 member ID
   */
//  @ApiModelProperty("과금 부과 member ID")
  @Column(name = "ag_charge_id")
  val agChargeId: String? = null
) : Serializable
