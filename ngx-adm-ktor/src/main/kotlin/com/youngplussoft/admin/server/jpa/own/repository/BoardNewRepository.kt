package com.youngplussoft.admin.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.common.repository.Querydsl4RepositorySupport
import com.youngplussoft.admin.server.jpa.own.entity.Board
import com.youngplussoft.admin.server.jpa.own.entity.BoardNew
import com.youngplussoft.admin.server.jpa.own.entity.QBoardNew
import kotlin.reflect.KClass


class BoardNewRepository : Querydsl4RepositorySupport<BoardNew>(Env.em, Board::class as KClass<Any>) {
  val path: PathBuilder<BoardNew> = PathBuilder<BoardNew>(BoardNew::class.java, "boardNew")
  val boardNew: QBoardNew = QBoardNew.boardNew


  override fun findById(id: String): BoardNew? {
    return selectFrom(boardNew).where(boardNew.id.eq(id)).fetchOne()
  }

  override fun findAll(offset: Long, limit: Long): List<BoardNew> {
    TODO("Not yet implemented")
  }

}
