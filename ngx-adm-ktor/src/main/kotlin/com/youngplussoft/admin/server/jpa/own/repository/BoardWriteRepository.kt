package com.youngplussoft.admin.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.common.repository.Querydsl4RepositorySupport
import com.youngplussoft.admin.server.jpa.own.entity.BoardWrite
import com.youngplussoft.admin.server.jpa.own.entity.QBoardWrite
import kotlin.reflect.KClass


class BoardWriteRepository : Querydsl4RepositorySupport<BoardWrite>(Env.em, BoardWrite::class as KClass<Any>) {
  val path: PathBuilder<BoardWrite> = PathBuilder<BoardWrite>(BoardWrite::class.java, "boardWrite")
  val boardWrite: QBoardWrite = QBoardWrite.boardWrite


  override fun findById(id: String): BoardWrite? {
    return selectFrom(boardWrite).where(boardWrite.id.eq(id)).fetchOne()
  }

  override fun findAll(offset: Long, limit: Long): List<BoardWrite> {
    TODO("Not yet implemented")
  }

}
