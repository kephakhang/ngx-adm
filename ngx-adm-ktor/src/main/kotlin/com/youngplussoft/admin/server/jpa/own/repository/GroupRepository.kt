package com.youngplussoft.admin.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.common.repository.Querydsl4RepositorySupport
import com.youngplussoft.admin.server.jpa.own.entity.Group
import com.youngplussoft.admin.server.jpa.own.entity.QGroup
import kotlin.reflect.KClass


class GroupRepository : Querydsl4RepositorySupport<Group>(Env.em, Group::class as KClass<Any>) {
  val path: PathBuilder<Group> = PathBuilder<Group>(Group::class.java, "group")
  val group: QGroup = QGroup.group


  override fun findById(id: String): Group? {
    return selectFrom(group).where(group.id.eq(id)).fetchOne()
  }

  override fun findAll(offset: Long, limit: Long): List<Group> {
    TODO("Not yet implemented")
  }
}
