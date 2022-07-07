package com.youngplussoft.admin.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.common.repository.Querydsl4RepositorySupport
import com.youngplussoft.admin.server.jpa.own.entity.CompanyMember
import com.youngplussoft.admin.server.jpa.own.entity.QCompanyMember
import kotlin.reflect.KClass


class CompanyMemberRepository : Querydsl4RepositorySupport<CompanyMember>(Env.em, CompanyMember::class as KClass<Any>) {
  val path: PathBuilder<CompanyMember> = PathBuilder<CompanyMember>(CompanyMember::class.java, "companyMember")
  val companyMember: QCompanyMember = QCompanyMember.companyMember

  override fun findById(id: String): CompanyMember? {
    return selectFrom(companyMember).where(companyMember.id.eq(id)).fetchOne()
  }

  override fun findAll(offset: Long, limit: Long): List<CompanyMember> {
    TODO("Not yet implemented")
  }

  fun findByCoIdAndMbId(coId: String, mbId: String): MutableList<CompanyMember>? {
    return selectFrom(companyMember)
      .where(
        companyMember.coId.eq(coId)
          .and(
            companyMember.mbId.eq(mbId)
          )
      ).fetch() as MutableList<CompanyMember>?
  }

}
