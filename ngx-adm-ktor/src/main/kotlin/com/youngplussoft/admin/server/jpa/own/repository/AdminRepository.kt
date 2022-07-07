package com.youngplussoft.admin.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.youngplussoft.admin.extensions.sha256
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.common.repository.Querydsl4RepositorySupport
import com.youngplussoft.admin.server.jpa.own.entity.*
import kotlin.reflect.KClass


class AdminRepository : Querydsl4RepositorySupport<Admin>(Env.em, Admin::class as KClass<Any>) {
  val path: PathBuilder<Admin> = PathBuilder<Admin>(Admin::class.java, "admin")
  val admin: QAdmin = QAdmin.admin


  override fun findById(id: String): Admin? {
    return selectFrom(admin).where(admin.id.eq(id)).fetchOne()
  }

  override fun findAll(offset: Long, limit: Long): List<Admin> {
    TODO("Not yet implemented")
  }

  fun findByEmail(email: String): Admin? {
    return selectFrom(admin).where(admin.amEmailHash.eq(email.sha256())).fetchOne()
  }
}
