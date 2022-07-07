package com.youngplussoft.admin.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.common.repository.Querydsl4RepositorySupport
import com.youngplussoft.admin.server.jpa.own.entity.Board
import com.youngplussoft.admin.server.jpa.own.entity.BoardGood
import com.youngplussoft.admin.server.jpa.own.entity.QBoardGood
import kotlin.reflect.KClass


class BoardGoodRepository : Querydsl4RepositorySupport<BoardGood>(Env.em, Board::class as KClass<Any>) {
  val path: PathBuilder<BoardGood> = PathBuilder<BoardGood>(BoardGood::class.java, "boardGood")
  val boardGood: QBoardGood = QBoardGood.boardGood


  override fun findById(id: String): BoardGood? {
    return selectFrom(boardGood).where(boardGood.id.eq(id)).fetchOne()
  }

  override fun findAll(offset: Long, limit: Long): List<BoardGood> {
    TODO("Not yet implemented")
  }

}
