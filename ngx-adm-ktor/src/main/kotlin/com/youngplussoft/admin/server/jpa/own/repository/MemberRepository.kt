package com.youngplussoft.admin.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.youngplussoft.admin.extensions.sha256
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.common.repository.Querydsl4RepositorySupport
import com.youngplussoft.admin.server.jpa.common.repository.WhereBuilder
import com.youngplussoft.admin.server.jpa.own.dto.UserDto
import com.youngplussoft.admin.server.jpa.own.dto.UserUpdateDto
import com.youngplussoft.admin.server.jpa.own.entity.Member
import com.youngplussoft.admin.server.jpa.own.entity.QMember
import com.youngplussoft.admin.server.jpa.own.enum.UserLevel
import java.time.Clock
import java.time.LocalDateTime
import javax.persistence.TypedQuery
import kotlin.reflect.KClass

class MemberRepository : Querydsl4RepositorySupport<Member>(Env.em, Member::class as KClass<Any>) {
  val path: PathBuilder<Member> = PathBuilder<Member>(Member::class.java, "member")
  val member: QMember = QMember.member


  override fun findById(id: String): Member? {
    return selectFrom(member).where(member.id.eq(id)).fetchOne()
  }

  override fun findAll(offset: Long, limit: Long): List<Member> {
    return selectFrom(member).offset(offset).limit(limit).fetch()
  }

  fun findByEmail(email: String): Member? {
    return selectFrom(member).where(member.mbEmailHash.eq(email.sha256())).fetchOne()
  }

  fun findByMobile(mobile: String): Member? {
    val m = mobile.replace("-", "")
    return selectFrom(member).where(member.mbMobileHash.eq(m.sha256())).fetchOne()
  }

  fun findAllByLevel(level: Int, offset: Long, limit: Long): List<Member> {
    if (level >= UserLevel.YoungPlusSoft.no) {
      return selectFrom(member).where(member.mbLevel.loe(level)).offset(offset).limit(limit).fetch()
    } else {
      return listOf()
    }
  }

  fun updateUser(userUpdateDto: UserUpdateDto) {
    queryFactory.update(member)
      .set(member.mbName, userUpdateDto.name)
      .set(member.mbLevel, userUpdateDto.level)
      .set(member.teId, userUpdateDto.tenantId)
      .set(member.modDatetime, LocalDateTime.now(Clock.systemUTC()))
      .where(member.id.eq(userUpdateDto.id)).execute()
  }
}
