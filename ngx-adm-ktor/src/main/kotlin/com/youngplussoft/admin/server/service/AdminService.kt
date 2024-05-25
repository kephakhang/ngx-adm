package com.youngplussoft.admin.server.service

import com.youngplussoft.admin.exception.YpsException
import com.youngplussoft.admin.exception.ErrorCode
import com.youngplussoft.admin.exception.YpsSessionNotFoundException
import com.youngplussoft.admin.extensions.isEmail
import com.youngplussoft.admin.extensions.sha256
import com.youngplussoft.admin.server.auth.BcryptHasher
import com.youngplussoft.admin.server.auth.JwtConfig
import com.youngplussoft.admin.server.jpa.own.dto.LoginDto
import com.youngplussoft.admin.server.jpa.own.dto.SignupDto
import com.youngplussoft.admin.server.jpa.own.dto.UserDto
import com.youngplussoft.admin.server.jpa.own.entity.Admin
import com.youngplussoft.admin.server.jpa.own.enum.AdminStatus
import com.youngplussoft.admin.server.jpa.own.enum.UserLevel
import com.youngplussoft.admin.server.jpa.own.repository.AdminRepository
import java.time.Clock
import java.time.LocalDateTime

class AdminService(val db: AdminRepository): BaseService<Admin>(db) {

  @Throws(Exception::class)
  fun login(credentials: LoginDto): UserDto = credentials.let { (id, password) ->
    val admin = if (id.isEmail()) {
      db.findByEmail(id) ?: throw YpsSessionNotFoundException(null, "User Not Found")
    } else {
      throw YpsSessionNotFoundException(null, "User Not Found")
    }
    val user: UserDto = admin.toDto()
    BcryptHasher.checkPassword(password, user)
    user.copy(jwt = JwtConfig.makeToken(user))
    return user
  }

  fun register(signup: SignupDto): UserDto {
    var admin = Admin()
    val now = LocalDateTime.now(Clock.systemUTC())
    admin.apply {
      amEmailHash = signup.email.sha256()
      amName = signup.name
      amPassword = BcryptHasher.hashPassword(signup.password)
      amLevel = UserLevel.ADMIN.no
      amStatus = AdminStatus.CERTIFIED.no
      createdAt = now
      updatedAt = now
    }

    db.transaction {
      admin = db.insert(admin) ?: throw YpsException(ErrorCode.E10003)
    }
    return admin.toDto()
  }

  fun updateAdmin(new: UserDto, current: UserDto): UserDto? {

    val curMember = db.findById(new.id!!) ?: throw YpsSessionNotFoundException(null, "Not Found User")
    var update = when {
      new.password != null -> new.copy(id = curMember.id.toString(), password = BcryptHasher.hashPassword(new.password))
      else -> new.copy(id = curMember.id.toString())
    }

    db.transaction {
      update = db.update(update.toAdmin()).toDto()
    }
    return update
  }
}

fun main() {
  print(BcryptHasher.hashPassword("osk3s#M!5ZYg-cz-h8uPH"))
}
