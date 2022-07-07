package com.youngplussoft.admin.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.common.repository.Querydsl4RepositorySupport
import com.youngplussoft.admin.server.jpa.own.entity.AgentDetail
import com.youngplussoft.admin.server.jpa.own.entity.QAgentDetail
import com.youngplussoft.admin.server.jpa.own.entity.Tenant
import kotlin.reflect.KClass


class AgentDetailRepository : Querydsl4RepositorySupport<AgentDetail>(Env.em, AgentDetail::class as KClass<Any>) {
  val path: PathBuilder<AgentDetail> = PathBuilder<AgentDetail>(AgentDetail::class.java, "agentDetail")
  val agentDetail: QAgentDetail = QAgentDetail.agentDetail

  // dummy overriding method
  override fun findById(id: String): AgentDetail? {
    return null
  }

  override fun findAll(offset: Long, limit: Long): List<AgentDetail> {
    TODO("Not yet implemented")
  }

}
