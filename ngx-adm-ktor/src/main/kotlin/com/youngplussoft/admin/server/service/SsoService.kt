package com.youngplussoft.admin.server.service

import com.youngplussoft.admin.common.KeyGenerator
import com.youngplussoft.admin.exception.YpsException
import com.youngplussoft.admin.exception.ErrorCode
import com.youngplussoft.admin.exception.YpsSessionNotFoundException
import com.youngplussoft.admin.extensions.isEmail
import com.youngplussoft.admin.extensions.isPhoneNumber
import com.youngplussoft.admin.extensions.sha256
import com.youngplussoft.admin.server.auth.BcryptHasher
import com.youngplussoft.admin.server.auth.JwtConfig
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.own.dto.LoginDto
import com.youngplussoft.admin.server.jpa.own.dto.SignupDto
import com.youngplussoft.admin.server.jpa.own.dto.UserDto
import com.youngplussoft.admin.server.jpa.own.entity.Member
import com.youngplussoft.admin.server.jpa.own.entity.MemberDetail
import com.youngplussoft.admin.server.jpa.own.enum.UserLevel
import com.youngplussoft.admin.server.jpa.own.enum.UserStatus
import com.youngplussoft.admin.server.jpa.own.repository.MemberDetailRepository
import com.youngplussoft.admin.server.jpa.own.repository.MemberRepository
import mu.KotlinLogging
import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneOffset

private val logger = KotlinLogging.logger {}

class SsoService(val db: MemberRepository, val detailDB: MemberDetailRepository) {

  @Throws(Exception::class)
  fun login(credentials: LoginDto): UserDto = credentials.let {
    val member = if (it.id.isEmail()) {
      db.findByEmail(it.id) ?: throw YpsSessionNotFoundException(null, "User Not Found")
    } else if (it.id.isPhoneNumber()) {
      db.findByMobile(it.id) ?: throw YpsSessionNotFoundException(null, "User Not Found")
    } else {
      throw YpsSessionNotFoundException(null, "User Not Found")
    }
    val user = member.toDto()
    BcryptHasher.checkPassword(it.password, user)
    val jwt = JwtConfig.makeToken(user)
    user.jwt = jwt
    member.mbJwt = jwt
    db.update(member)
    return user
  }

  @Throws(Exception::class)
  fun checkAvailable(loginId: String): Boolean {
    return if (loginId.isEmail()) {
      if (db.findByEmail(loginId) != null) false else true
    } else if (loginId.isPhoneNumber()) {
      if (db.findByMobile(loginId) != null) false else true
    } else {
      false
    }
  }


  @Throws(Exception::class)
  fun register(signup: SignupDto): UserDto {
    var member = Member()
    member.apply {
      teId = Env.YoungPlusSoftTenantId
      mbEmailHash = signup.email.sha256()
      mbMobileHash = signup.mobile.sha256()
      mbName = signup.name
      mbPassword = BcryptHasher.hashPassword(signup.password)
      mbLevel = UserLevel.GUEST.no
      mbStatus = UserStatus.WAIT.no
    }

    var memberDetail = MemberDetail()
    try {
      db.transaction {
        member = db.insert(member)
        memberDetail.apply {
          mbId = member.id!!
          teId = member.teId
          mbEmail = signup.email
          mbMobile = signup.mobile
          mbEmailCertify = LocalDateTime.now(Clock.systemUTC())
          mbEmailCertify2 = KeyGenerator.generateMobileAuth()
        }
        memberDetail.mbTodayLogin = memberDetail.mbEmailCertify
        memberDetail = detailDB.insert(memberDetail)
        member.detail = memberDetail
      }
    } catch(ex: YpsException) {
      if (ex.code.name.equals(ErrorCode.E10007.name)) { // if db insert dup error
        // The User is already registered.
        throw YpsException(ErrorCode.E10008, ex, null, signup.email)
      } else {
        throw ex
      }
    }

//    val content = Env.confirmEmailContent.replace("{{action_url}}", Env.apiHostUrl + "/sso/confirm/email?id=${member.id}&confirm=${memberDetail.mbEmailCertify2}")
//    Env.mailSender.sendEmail(Email(Env.email, "[YoungPlusSoft] Thank you for singup ::: Email Certification", content, memberDetail.mbEmail!!))
    return member.toDto()
  }

  @Throws(Exception::class)
  fun confirmEmail(uid: String, confirm: String): String? {
    try {
      val member = db.findById(uid) ?: return "User Not Found"
      val now = Env.now()
      if ((now.toEpochSecond(ZoneOffset.UTC) - member.detail?.mbEmailCertify!!.toEpochSecond(ZoneOffset.UTC)) > Env.confirmExpireTime) {
        return "Confirmation time was expired !!!"
      } else if (member.detail?.mbEmailCertify2 != confirm) {
        return "Confirmation code is wrong : Please find the confirmation email in your email inbox"
      } else {
        member.mbStatus =
          if (member.mbStatus === UserStatus.MOBILE_ONLY.no) UserStatus.BOTH.no else UserStatus.EMAIL_ONLY.no
        db.transaction {
          db.update(member)
        }
        return null //정상
      }
    } catch(ex: Exception) {
      logger.error("sso.confirmEmail","Unknown Error : ${ex}")
      return ex.localizedMessage
    }
  }

  fun updateUser(new: UserDto, current: UserDto): UserDto? {

    val curMember = db.findById(new.id!!) ?: throw YpsSessionNotFoundException(null, "Not Found User")
    var update = when {
      new.password != null -> new.copy(id = curMember.id.toString(), password = BcryptHasher.hashPassword(new.password))
      else -> new.copy(id = curMember.id.toString())
    }

    db.transaction {
      update = db.update(update.toEntity()).toDto()
    }
    return update
  }
}

fun main() {
  print(BcryptHasher.hashPassword("osk3s#M!5ZYg-cz-h8uPH"))
}
