package com.youngplussoft.admin.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.youngplussoft.admin.extensions.sha256
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.common.repository.Querydsl4RepositorySupport
import com.youngplussoft.admin.server.jpa.own.entity.Agent
import com.youngplussoft.admin.server.jpa.own.entity.QAgent
import kotlin.reflect.KClass


class AgentRepository : Querydsl4RepositorySupport<Agent>(Env.em, Agent::class as KClass<Any>) {
  val path: PathBuilder<Agent> = PathBuilder<Agent>(Agent::class.java, "agent")
  val agent: QAgent = QAgent.agent


  override fun findById(id: String): Agent? {
    return selectFrom(agent).where(agent.id.eq(id)).fetchOne()
  }

  override fun findAll(offset: Long, limit: Long): List<Agent> {
    TODO("Not yet implemented")
  }

  fun findByEmail(email: String): Agent? {
    return selectFrom(agent).where(agent.agEmailHash.eq(email.sha256())).fetchOne()
  }

  fun findByMobile(mobile: String): Agent? {
    return selectFrom(agent).where(agent.agMobileHash.eq(mobile.sha256())).fetchOne()
  }
}
