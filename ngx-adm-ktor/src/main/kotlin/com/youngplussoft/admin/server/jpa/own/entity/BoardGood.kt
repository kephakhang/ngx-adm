package com.youngplussoft.admin.server.jpa.own.entity

import com.fasterxml.jackson.annotation.JsonInclude
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.common.entity.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity(name = "BoardGood")
@Table(name = Env.tablePrefix + "board_good")
@JsonInclude(JsonInclude.Include.NON_NULL)
open class BoardGood(

  @Column(name = "bo_id", nullable = false)
  var boId: String = "",

  @Column(name = "wr_id", nullable = false)
  var wrId: String = "",

  @Column(name = "mb_id", nullable = false)
  var mbId: String = "",

  @Column(name = "bg_flag", nullable = false)
  var bgFlag: String = ""

) : BaseEntity<BoardGood>() {
  override fun toDto(): BoardGood {
    return BoardGood(
      //ToDo add fields ...
    )
  }
}
