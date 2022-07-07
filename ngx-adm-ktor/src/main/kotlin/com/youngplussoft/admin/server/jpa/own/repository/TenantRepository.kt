package com.youngplussoft.admin.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.youngplussoft.admin.extensions.sha256
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.common.repository.Querydsl4RepositorySupport
import com.youngplussoft.admin.server.jpa.common.repository.WhereBuilder
import com.youngplussoft.admin.server.jpa.own.entity.Tenant
import com.youngplussoft.admin.server.jpa.own.entity.QTenant
import javax.persistence.TypedQuery
import kotlin.reflect.KClass

class TenantRepository : Querydsl4RepositorySupport<Tenant>(Env.em, Tenant::class as KClass<Any>) {
  val path: PathBuilder<Tenant> = PathBuilder<Tenant>(Tenant::class.java, "tenant")
  val tenant: QTenant = QTenant.tenant


  override fun findById(id: String): Tenant? {
    return selectFrom(tenant).where(tenant.id.eq(id)).fetchOne()
  }

  override fun findAll(offset: Long, limit: Long): List<Tenant> {
    return selectFrom(tenant).offset(offset).limit(limit).fetch()
  }
}
