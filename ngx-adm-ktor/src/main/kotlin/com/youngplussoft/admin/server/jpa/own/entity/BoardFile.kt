package com.youngplussoft.admin.server.jpa.own.entity

import com.fasterxml.jackson.annotation.JsonInclude
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.common.entity.BaseEntity
import com.youngplussoft.admin.server.jpa.own.dto.BoardDto
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table


@Entity(name = "BoardFile")
@Table(name = Env.tablePrefix + "board_file")
@JsonInclude(JsonInclude.Include.NON_NULL)
open class BoardFile(


  /**
   * 게시판 정보 테이블 Join ID
   */
//  @ApiModelProperty("게시판 정보 테이블 Join ID")
  @Column(name = "bo_id", nullable = false)
  var boId: String = "",
  /**
   * 게시판 작성(write) 글 고유 ID
   */
  @Column(name = "wr_id", nullable = false)
//  @ApiModelProperty("게시판 작성(write) 글 고유 ID")
  var wrId: String = "",

  /**
   * 게시판 1개 글에 첨부된 파일 순번
   */
//  @ApiModelProperty("게시판 1개 글에 첨부된 파일 순번")
  @Column(name = "bf_no", nullable = false)
  var bfNo: Int = 0,

  /**
   * 첨부파일 원래 이름
   */
//  @ApiModelProperty("첨부파일 원래 이름")
  @Column(name = "bf_source", nullable = false)
  var bfSource: String? = null,

  /**
   * 첨부파일 upload 후 생성된 파일명
   */
//  @ApiModelProperty("첨부파일 upload 후 생성된 파일명")
  @Column(name = "bf_fname", nullable = false)
  var bfFname: String? = null,

  /**
   * 첨부파일 위치 or url
   */
//  @ApiModelProperty("첨부파일 위치 or url")
  @Column(name = "bf_path", nullable = false)
  var bfPath: String? = null,

  /**
   * 다운로드된 수
   */
//  @ApiModelProperty("다운로드된 수")
  @Column(name = "bf_download", nullable = false)
  var bfDownload: Int = 0,

  /**
   * 메타 정보(필요한경우)
   */
  @Column(name = "bf_content", columnDefinition = "text")
//  @ApiModelProperty("메타 정보(필요한경우)")
  var bfContent: String? = null,

  @Column(name = "bf_filesize", nullable = false)
  var bfFilesize: Int? = null,

  @Column(name = "bf_width", nullable = false)
  var bfWidth: Int? = null,

  @Column(name = "bf_height", columnDefinition="smallint", nullable = false)
  var bfHeight: Int? = null,

  /**
   * 0:deleted, 1:use
   */
//  @ApiModelProperty("0:deleted, 1:use")
  @Column(name = "bf_status", nullable = false)
  var bfStatus: Int = 1,

  /**
   * jpg, git, png, mp3, mp4, pdf...
   */
  @Column(name = "bf_ext", nullable = false)
//  @ApiModelProperty("jpg, git, png, mp3, mp4, pdf...")
  var bfExt: String? = null

) : BaseEntity<BoardFile>() {

  override fun toDto(): BoardFile {
    return BoardFile(
      //ToDo add fields ...
    )
  }
}
