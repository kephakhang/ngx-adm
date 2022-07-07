package com.youngplussoft.admin.server.jpa.own.entity

import com.fasterxml.jackson.annotation.JsonInclude
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.common.entity.BaseEntity
import javax.persistence.*

@Entity(name = "BoardWrite")
@Table(name = Env.tablePrefix + "board_write")
@JsonInclude(JsonInclude.Include.NON_NULL)
open class BoardWrite(

  @Column(name = "bo_id", nullable = false)
  var boId: String = "",

  @Column(name = "mb_id", nullable = false)
  var mbId: String? = null,

  @Column(name = "wr_subject", nullable = false)
  var wrSubject: String = "",

  @Column(name = "wr_name", nullable = false)
  var wrName: String = "",

  @Column(name = "wr_num", nullable = false)
  var wrNum: Int = 0,

  @Column(name = "wr_reply", nullable = false)
  var wrReply: String = "",

  @Column(name = "wr_parent", nullable = false)
  var wrParent: Int = 0,

  @Column(name = "wr_is_comment", nullable = false)
  var wrIsComment: Int = 0,

  @Column(name = "wr_comment", nullable = false)
  var wrComment: Int = 0,

  @Column(name = "wr_comment_reply", nullable = false)
  var wrCommentReply: String = "",

//  @ApiModelProperty("게시판 내 서브 카테고리")
  @Column(name = "wr_category", nullable = false)
  var wrCategory: String = "",

//  @ApiModelProperty("html1:0, html2:1, secret:2, mail:3")
  @Column(name = "wr_option", nullable = false)
  var wrOption: Int = 0,

  @Column(name = "wr_hit", nullable = false)
  var wrHit: Int = 0,

  @Column(name = "wr_good", nullable = false)
  var wrGood: Int = 0,

  @Column(name = "wr_nogood", nullable = false)
  var wrNogood: Int = 0,

  @Column(name = "wr_file", nullable = false)
  var wrFile: Int = 0,

  @OneToOne(fetch = FetchType.LAZY, mappedBy = "boardWrite", cascade = [CascadeType.ALL])
  var detail: BoardWriteDetail? = null

) : BaseEntity<BoardWrite>() {
  override fun toDto(): BoardWrite {
    return BoardWrite(
      //ToDo add fields ...
    )
  }
}