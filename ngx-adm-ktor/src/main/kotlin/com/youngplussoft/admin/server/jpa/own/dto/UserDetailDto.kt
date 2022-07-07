package com.youngplussoft.admin.server.jpa.own.dto

import com.youngplussoft.admin.common.DateUtil
import com.youngplussoft.admin.server.jpa.own.entity.MemberDetail

data class UserDetailDto(
  val tenantId: String? = null,
  val mobile: String? = null,
  val email: String? = null,
  val image: String? = null,
  val homepage: String? = null,
  val gender: String? = null,
  val birthday: String? = null,
  val adult: Boolean = false,
  val married: Boolean = false,
  val zip: String? = null,
  val addr1: String? = null,
  val addr2: String? = null,
  val addr3: String? = null,
  val addrJibeon: String? = null,
  val latitude: Double? = null,
  val longitude: Double? = null,
  val signature: String? = null,
  val recommend: String? = null,
  val todayLogin: String? = null,
  val loginIp: String? = null,
  val ip: String? = null,
  val leaveDate: String? = null,
  val interceptDate: String? = null,
  val emailCertify: String? = null,
  val emailCertify2: String? = null,
  val mobileCertify: String? = null,
  val mobileCertify2: String? = null,
  val memo: String? = null,
  val lostCertify: String? = null,
  val mailling: Boolean = false,
  val sms: Boolean = false,
  val open: Boolean = false,
  val openDate: String? = null,
  val greeting: String? = null,
  val memoCall: String? = null,
  val memoCnt: Int? = 0,
  val scrapCnt: Int? = 0,
  val ext1: String? = null,
  val ext2: String? = null,
  val ext3: String? = null,
  val ext4: String? = null,
  val ext5: String? = null,
  val ext6: String? = null,
  val ext7: String? = null,
  val ext8: String? = null,
  val ext9: String? = null,
  val ext10: String? = null
): BasetDto<MemberDetail>() {

  override fun toEntity(): MemberDetail {
    val memberDetail = MemberDetail()
    return memberDetail.apply {
      teId = tenantId
      mbEmail = email
      mbMobile = mobile
      mbImage = image
      mbHomepage = homepage
      mbGender = gender
      mbBirthday = DateUtil.parseLocalDate(birthday)
      mbAdult = adult
      mbMarried = married
      mbZip = zip
      mbAddr1 = addr1
      mbAddr2 = addr2
      mbAddr3 = addr3
      mbAddrJibeon = addrJibeon
      mbSignature = signature
      mbRecommend = recommend
      mbTodayLogin = DateUtil.parseLocalDatetime(todayLogin)
      mbLoginIp = loginIp
      mbIp = ip
      mbLeaveDate = DateUtil.parseLocalDate(leaveDate)
      mbInterceptDate = DateUtil.parseLocalDate(interceptDate)
      mbEmailCertify = DateUtil.parseLocalDatetime(emailCertify)
      mbEmailCertify2 = emailCertify2
      mbMobileCertify = DateUtil.parseLocalDatetime(mobileCertify)
      mbMobileCertify2 = mobileCertify2
      mbMemo = memo
      mbLostCertify = lostCertify
      mbMailling = mailling
      mbSms = sms
      mbOpen = open
      mbOpenDate = DateUtil.parseLocalDate(openDate)
      mbGreeting = greeting
      mbMemoCall = memoCall
      mbMemoCnt = memoCnt
      mbScrapCnt = scrapCnt
      mb1 = ext1
      mb2 = ext2
      mb3 = ext3
      mb4 = ext4
      mb5 = ext5
      mb6 = ext6
      mb7 = ext7
      mb8 = ext8
      mb9 = ext9
      mb10 = ext10
    }
  }
}

class UserDetailWrapper(val userDetail: UserDetailDto)
