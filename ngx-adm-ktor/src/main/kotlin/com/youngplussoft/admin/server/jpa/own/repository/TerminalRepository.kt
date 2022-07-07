package com.youngplussoft.admin.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.common.repository.Querydsl4RepositorySupport
import com.youngplussoft.admin.server.jpa.common.repository.WhereBuilder
import com.youngplussoft.admin.server.jpa.own.entity.Terminal
import com.youngplussoft.admin.server.jpa.own.entity.QTerminal
import kotlin.reflect.KClass

class TerminalRepository : Querydsl4RepositorySupport<Terminal>(Env.em, Terminal::class as KClass<Any>) {
  val path: PathBuilder<Terminal> = PathBuilder<Terminal>(Terminal::class.java, "terminal")
  val terminal: QTerminal = QTerminal.terminal


    override fun findById(id: String): Terminal? {
    return selectFrom(terminal).where(terminal.id.eq(id)).fetchOne()
  }

    override fun findAll(offset: Long, limit: Long): List<Terminal> {
        return selectFrom(terminal).offset(offset).limit(limit).fetch()
    }

    fun findAllByTeId(tenantId: String?, offset: Long=0, limit: Long=300): List<Terminal> {
    return selectFrom(terminal).where(WhereBuilder()
              .optionalAnd(tenantId, { terminal.teId.eq(tenantId) })
            ).offset(offset).limit(limit).fetch()
  }
}
