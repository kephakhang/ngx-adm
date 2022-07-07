package com.youngplussoft.admin.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.common.repository.Querydsl4RepositorySupport
import com.youngplussoft.admin.server.jpa.own.entity.Board
import com.youngplussoft.admin.server.jpa.own.entity.QBoard
import kotlin.reflect.KClass


class BoardRepository : Querydsl4RepositorySupport<Board>(Env.em, Board::class as KClass<Any>) {
  val path: PathBuilder<Board> = PathBuilder<Board>(Board::class.java, "board")
  val board: QBoard = QBoard.board


  override fun findById(id: String): Board? {
    return selectFrom(board).where(board.id.eq(id)).fetchOne()
  }

  override fun findAll(offset: Long, limit: Long): List<Board> {
    TODO("Not yet implemented")
  }
}
