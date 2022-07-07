package com.youngplussoft.admin.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.common.repository.Querydsl4RepositorySupport
import com.youngplussoft.admin.server.jpa.common.repository.WhereBuilder
import com.youngplussoft.admin.server.jpa.own.entity.Counter
import com.youngplussoft.admin.server.jpa.own.entity.QCounter
import kotlin.reflect.KClass

class CounterRepository : Querydsl4RepositorySupport<Counter>(Env.em, Counter::class as KClass<Any>) {
  val path: PathBuilder<Counter> = PathBuilder<Counter>(Counter::class.java, "counter")
  val counter: QCounter = QCounter.counter


  override fun findById(id: String): Counter? {
    return selectFrom(counter).where(counter.id.eq(id)).fetchOne()
  }

  override fun findAll(offset: Long, limit: Long): List<Counter> {
    TODO("Not yet implemented")
  }

  fun findAllByTeIdAndTrId(tenantId: String?, terminalId: String?, offset: Long=0, limit: Long=300): List<Counter> {
    return selectFrom(counter).where(WhereBuilder()
              .optionalAnd(tenantId, { counter.teId.eq(tenantId) })
              .optionalAnd(terminalId, { counter.trId.eq(terminalId) })
    ).offset(offset).limit(limit).fetch()
  }
}
