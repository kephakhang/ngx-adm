package com.youngplussoft.admin.server.jpa.own.entity

import com.youngplussoft.admin.common.DateUtil
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.own.dto.UserDetailDto
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "MemberDetail")
@Table(name = Env.tablePrefix + "member_detail")
@Cacheable
@JsonInclude(JsonInclude.Include.NON_NULL)
open class MemberDetail(

  @JsonIgnore
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mb_id", insertable = false, updatable = false)
  var member: Member? = null,


  @Id
  @Column(name = "mb_id", updatable = false)
  var mbId: String? = null,

  /**
   * tenant ID(UUID)
   */
//  @ApiModelProperty("고유 UUID")
  @Column(name = "te_id")
  var teId: String? = null,

  @Column(name = "mb_mobile", nullable = false)
  var mbMobile: String? = null,

  @Column(name = "mb_email", nullable = false)
  var mbEmail: String? = null,

  @Column(name = "mb_homepage")
  var mbHomepage: String? = null,

  @Column(name = "mb_image")
  var mbImage: String? = null,

  @Column(name = "mb_gender", columnDefinition = "char(1)")
  var mbGender: String? = null,

  @Column(name = "mb_birthday")
  var mbBirthday: LocalDate? = null,

  @Column(name = "mb_adult")
  var mbAdult: Boolean = false,

  @Column(name = "mb_married")
  var mbMarried: Boolean = false,

  @Column(name = "mb_zip")
  var mbZip: String? = null,

  @Column(name = "mb_addr1")
  var mbAddr1: String? = null,

  @Column(name = "mb_addr2")
  var mbAddr2: String? = null,

  @Column(name = "mb_addr3")
  var mbAddr3: String? = null,

  @Column(name = "mb_addr_jibeon")
  var mbAddrJibeon: String? = null,

  @Column(name = "mb_latitude", columnDefinition = "double")
  var mbLatitude: Double? = null,

  @Column(name = "mb_longitude", columnDefinition = "double")
  var mbLongitude: Double? = null,

  @Column(name = "mb_signature", columnDefinition = "text")
  var mbSignature: String? = null,

  @Column(name = "mb_recommend")
  var mbRecommend: String? = null,

  @Column(name = "mb_today_login")
  //@Contextual
  var mbTodayLogin: LocalDateTime? = null,

  @Column(name = "mb_login_ip")
  var mbLoginIp: String? = null,

  @Column(name = "mb_ip")
  var mbIp: String? = null,

  @Column(name = "mb_leave_date")
  //@Contextual
  var mbLeaveDate: LocalDate? = null,

  @Column(name = "mb_intercept_date")
  var mbInterceptDate: LocalDate? = null,

  @Column(name = "mb_email_certify")
  var mbEmailCertify: LocalDateTime? = null,

  @Column(name = "mb_email_certify2")
  var mbEmailCertify2: String? = null,

  @Column(name = "mb_mobile_certify")
  var mbMobileCertify: LocalDateTime? = null,

  @Column(name = "mb_mobile_certify2")
  var mbMobileCertify2: String? = null,

  @Column(name = "mb_memo", columnDefinition = "text")
  var mbMemo: String? = null,

  @Column(name = "mb_lost_certify")
  var mbLostCertify: String? = null,

  @Column(name = "mb_mailling")
  var mbMailling: Boolean = false,

  @Column(name = "mb_sms")
  var mbSms: Boolean = false,

  @Column(name = "mb_open")
  var mbOpen: Boolean = false,

  @Column(name = "mb_open_date")
  var mbOpenDate: LocalDate? = null,

  @Column(name = "mb_greeting", columnDefinition = "text")
  var mbGreeting: String? = null,

  @Column(name = "mb_memo_call")
  var mbMemoCall: String? = null,

  @Column(name = "mb_memo_cnt")
  var mbMemoCnt: Int? = 0,

  @Column(name = "mb_scrap_cnt")
  var mbScrapCnt: Int? = 0,

  @Column(name = "mb_1")
  var mb1: String? = null,

  @Column(name = "mb_2")
  var mb2: String? = null,

  @Column(name = "mb_3")
  var mb3: String? = null,

  @Column(name = "mb_4")
  var mb4: String? = null,

  @Column(name = "mb_5")
  var mb5: String? = null,

  @Column(name = "mb_6")
  var mb6: String? = null,

  @Column(name = "mb_7")
  var mb7: String? = null,

  @Column(name = "mb_8")
  var mb8: String? = null,

  @Column(name = "mb_9")
  var mb9: String? = null,

  @Column(name = "mb_10")
  var mb10: String? = null

) : Serializable {


  fun toDto(): UserDetailDto {
    return UserDetailDto(
      tenantId = teId,
      email = mbEmail,
      mobile = mbMobile,
      homepage = mbHomepage,
      gender = mbGender,
      birthday = DateUtil.localDateToStr(mbBirthday),
      adult = mbAdult,
      married = mbMarried,
      zip = mbZip,
      addr1 = mbAddr1,
      addr2 = mbAddr2,
      addr3 = mbAddr3,
      addrJibeon = mbAddrJibeon,
      signature = mbSignature,
      latitude = mbLatitude,
      longitude = mbLongitude,
      recommend = mbRecommend,
      todayLogin = DateUtil.localDatetimeToStr(mbTodayLogin),
      loginIp = mbLoginIp,
      ip = mbIp,
      leaveDate = DateUtil.localDateToStr(mbLeaveDate),
      interceptDate = DateUtil.localDateToStr(mbInterceptDate),
      emailCertify = DateUtil.localDatetimeToStr(mbEmailCertify),
      emailCertify2 = mbEmailCertify2,
      mobileCertify = DateUtil.localDatetimeToStr(mbMobileCertify),
      mobileCertify2 = mbMobileCertify2,
      memo = mbMemo,
      lostCertify = mbLostCertify,
      mailling = mbMailling,
      sms = mbSms,
      open = mbOpen,
      openDate = DateUtil.localDateToStr(mbOpenDate),
      greeting = mbGreeting,
      memoCall = mbMemoCall,
      memoCnt = mbMemoCnt,
      scrapCnt = mbScrapCnt,
      ext1 = mb1,
      ext2 = mb2,
      ext3 = mb3,
      ext4 = mb4,
      ext5 = mb5,
      ext6 = mb6,
      ext7 = mb7,
      ext8 = mb8,
      ext9 = mb9,
      ext10 = mb10
    )
  }
}
