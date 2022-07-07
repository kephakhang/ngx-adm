package com.youngplussoft.admin.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.common.repository.Querydsl4RepositorySupport
import com.youngplussoft.admin.server.jpa.own.entity.AgentDetail
import com.youngplussoft.admin.server.jpa.own.entity.MemberDetail
import com.youngplussoft.admin.server.jpa.own.entity.QMemberDetail
import kotlin.reflect.KClass


class MemberDetailRepository : Querydsl4RepositorySupport<MemberDetail>(Env.em, MemberDetail::class as KClass<Any>) {
  val path: PathBuilder<MemberDetail> = PathBuilder<MemberDetail>(MemberDetail::class.java, "memberDetail")
  val memberDetail: QMemberDetail = QMemberDetail.memberDetail

  // dummy overriding method
  override fun findById(id: String): MemberDetail? {
    return null
  }

  override fun findAll(offset: Long, limit: Long): List<MemberDetail> {
    TODO("Not yet implemented")
  }
}
