package com.youngplussoft.admin.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.common.repository.Querydsl4RepositorySupport
import com.youngplussoft.admin.server.jpa.own.entity.Company
import com.youngplussoft.admin.server.jpa.own.entity.QCompany
import kotlin.reflect.KClass


class CompanyRepository : Querydsl4RepositorySupport<Company>(Env.em, Company::class as KClass<Any>) {
  val path: PathBuilder<Company> = PathBuilder<Company>(Company::class.java, "company")
  val company: QCompany = QCompany.company

  override fun findById(id: String): Company? {
    return selectFrom(company).where(company.id.eq(id)).fetchOne()
  }

  override fun findAll(offset: Long, limit: Long): List<Company> {
    TODO("Not yet implemented")
  }

}
