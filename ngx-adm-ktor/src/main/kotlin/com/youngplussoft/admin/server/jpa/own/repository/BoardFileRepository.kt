package com.youngplussoft.admin.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.common.repository.Querydsl4RepositorySupport
import com.youngplussoft.admin.server.jpa.own.entity.Board
import com.youngplussoft.admin.server.jpa.own.entity.BoardFile
import com.youngplussoft.admin.server.jpa.own.entity.QBoardFile
import kotlin.reflect.KClass


class BoardFileRepository : Querydsl4RepositorySupport<BoardFile>(Env.em, Board::class as KClass<Any>) {
  val path: PathBuilder<BoardFile> = PathBuilder<BoardFile>(BoardFile::class.java, "boardFile")
  val boardFile: QBoardFile = QBoardFile.boardFile


  override fun findById(id: String): BoardFile? {
    return selectFrom(boardFile).where(boardFile.id.eq(id)).fetchOne()
  }

  override fun findAll(offset: Long, limit: Long): List<BoardFile> {
    TODO("Not yet implemented")
  }

}
