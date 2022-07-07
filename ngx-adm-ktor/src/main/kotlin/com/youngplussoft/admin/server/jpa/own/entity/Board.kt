package com.youngplussoft.admin.server.jpa.own.entity


import com.fasterxml.jackson.annotation.JsonInclude
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.common.entity.BaseEntity
import com.youngplussoft.admin.server.jpa.own.dto.BoardDto
import com.youngplussoft.admin.server.jpa.own.dto.UserDto
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity(name = "Board")
@Table(name = Env.tablePrefix + "board")
@JsonInclude(JsonInclude.Include.NON_NULL)
open class Board(

  /**
   * 게시판 테이블명
   */
//  @ApiModelProperty("게시판 테이블명")
  @Column(name = "bo_table", nullable = false)
  var boTable: String? = null,

  /**
   * 게시판 그룹 고유 ID
   */
//  @ApiModelProperty("게시판 그룹 고유 ID")
  @Column(name = "gr_id", nullable = false)
  var grId: String? = null,

  @Column(name = "bo_subject", nullable = false)
  var boSubject: String? = null,

  @Column(name = "bo_mobile_subject", nullable = false)
  var boMobileSubject: String? = null,

  /**
   * 0:pc, 1:mobile, 2:both
   */
//  @ApiModelProperty("0:pc, 1:mobile, 2:both")
  @Column(name = "bo_device", nullable = false)
  var boDevice: Int = 2,

  @Column(name = "bo_admin")
  var boAdmin: String? = null,

  @Column(name = "bo_list_level", nullable = false)
  var boListLevel: Int = 1,

  @Column(name = "bo_read_level", nullable = false)
  var boReadLevel: Int = 1,

  @Column(name = "bo_write_level", nullable = false)
  var boWriteLevel: Int = 1,

  @Column(name = "bo_reply_level", nullable = false)
  var boReplyLevel: Int = 1,

  @Column(name = "bo_comment_level", nullable = false)
  var boCommentLevel: Int = 1,

  @Column(name = "bo_upload_level", nullable = false)
  var boUploadLevel: Int = 1,

  @Column(name = "bo_download_level", nullable = false)
  var boDownloadLevel: Int = 1,

  @Column(name = "bo_html_level", nullable = false)
  var boHtmlLevel: Int = 1,

  @Column(name = "bo_link_level", nullable = false)
  var boLinkLevel: Int = 1,

  @Column(name = "bo_count_delete", nullable = false)
  var boCountDelete: Int = 1,

  @Column(name = "bo_count_modify", nullable = false)
  var boCountModify: Int = 1,

  @Column(name = "bo_read_point", nullable = false)
  var boReadPoint: Int = -1,

  @Column(name = "bo_write_point", nullable = false)
  var boWritePoint: Int = 5,

  @Column(name = "bo_comment_point", nullable = false)
  var boCommentPoint: Int = 1,

  @Column(name = "bo_download_point", nullable = false)
  var boDownloadPoint: Int = -20,

  @Column(name = "bo_use_category", nullable = false)
  var boUseCategory: Int = 0,

//  @ApiModelProperty("board sub categoy list : text field")
  @Column(name = "bo_category_list", columnDefinition = "text")
  var boCategoryList: String? = null,

  @Column(name = "bo_use_sideview", nullable = false)
  var boUseSideview: Int = 0,

  @Column(name = "bo_use_file_content", nullable = false)
  var boUseFileContent: Int = 0,

  @Column(name = "bo_use_secret", nullable = false)
  var boUseSecret: Int = 0,

  @Column(name = "bo_use_dhtml_editor", nullable = false)
  var boUseDhtmlEditor: Int = 0,

  @Column(name = "bo_select_editor", nullable = false)
  var boSelectEditor: String = "",

  @Column(name = "bo_use_rss_view", nullable = false)
  var boUseRssView: Int = 0,

  @Column(name = "bo_use_good", nullable = false)
  var boUseGood: Int = 0,

  @Column(name = "bo_use_nogood", nullable = false)
  var boUseNogood: Int = 0,

  @Column(name = "bo_use_name", nullable = false)
  var boUseName: Int = 0,

  @Column(name = "bo_use_signature", nullable = false)
  var boUseSignature: Int = 0,

  @Column(name = "bo_use_ip_view", nullable = false)
  var boUseIpView: Int = 0,

  @Column(name = "bo_use_list_view", nullable = false)
  var boUseListView: Int = 0,

  @Column(name = "bo_use_list_file", nullable = false)
  var boUseListFile: Int = 0,

  @Column(name = "bo_use_list_content", nullable = false)
  var boUseListContent: Int = 0,

  @Column(name = "bo_table_width", nullable = false)
  var boTableWidth: Int = 128,

  @Column(name = "bo_subject_len", nullable = false)
  var boSubjectLen: Int = 70,

  @Column(name = "bo_mobile_subject_len", nullable = false)
  var boMobileSubjectLen: Int = 30,

  @Column(name = "bo_page_rows", nullable = false)
  var boPageRows: Int = 15,

  @Column(name = "bo_mobile_page_rows", nullable = false)
  var boMobilePageRows: Int = 15,

  @Column(name = "bo_new", nullable = false)
  var boNew: Int = 24,

  @Column(name = "bo_hot", nullable = false)
  var boHot: Int = 100,

  @Column(name = "bo_image_width", nullable = false)
  var boImageWidth: Int = 640,

  @Column(name = "bo_skin", nullable = false)
  var boSkin: String = "basic",

  @Column(name = "bo_mobile_skin", nullable = false)
  var boMobileSkin: String = "basic",

  @Column(name = "bo_include_head")
  var boIncludeHead: String? = null,

  @Column(name = "bo_include_tail")
  var boIncludeTail: String? = null,

  @Column(name = "bo_content_head", columnDefinition = "text", nullable = false)
  var boContentHead: String? = null,

  @Column(name = "bo_mobile_content_head", columnDefinition = "text", nullable = false)
  var boMobileContentHead: String? = null,

  @Column(name = "bo_content_tail", columnDefinition = "text", nullable = false)
  var boContentTail: String? = null,

  @Column(name = "bo_mobile_content_tail", columnDefinition = "text", nullable = false)
  var boMobileContentTail: String? = null,

  @Column(name = "bo_insert_content", columnDefinition = "text", nullable = false)
  var boInsertContent: String? = null,

  @Column(name = "bo_gallery_cols", nullable = false)
  var boGalleryCols: Int = 4,

  @Column(name = "bo_gallery_width", nullable = false)
  var boGalleryWidth: Int = 128,

  @Column(name = "bo_gallery_height", nullable = false)
  var boGalleryHeight: Int = 128,

  @Column(name = "bo_mobile_gallery_width", nullable = false)
  var boMobileGalleryWidth: Int = 100,

  @Column(name = "bo_mobile_gallery_height", nullable = false)
  var boMobileGalleryHeight: Int = 100,

  @Column(name = "bo_upload_size", nullable = false)
  var boUploadSize: Int = 104857600,

  @Column(name = "bo_reply_order", nullable = false)
  var boReplyOrder: Int = 1,

  @Column(name = "bo_use_search", nullable = false)
  var boUseSearch: Int = 0,

  @Column(name = "bo_order", nullable = false)
  var boOrder: Int = 0,

  @Column(name = "bo_count_write", nullable = false)
  var boCountWrite: Int = 0,

  @Column(name = "bo_count_comment", nullable = false)
  var boCountComment: Int = 0,

  @Column(name = "bo_write_min", nullable = false)
  var boWriteMin: Int = 0,

  @Column(name = "bo_write_max", nullable = false)
  var boWriteMax: Int = 0,

  @Column(name = "bo_comment_min", nullable = false)
  var boCommentMin: Int = 0,

  @Column(name = "bo_comment_max", nullable = false)
  var boCommentMax: Int = 0,

  @Column(name = "bo_notice", columnDefinition = "text", nullable = false)
  var boNotice: String? = null,

  @Column(name = "bo_upload_count", nullable = false)
  var boUploadCount: Int = 0,

  @Column(name = "bo_use_email", nullable = false)
  var boUseEmail: Int = 0,

  /**
   * general:0, cert:1, adult:2, hp-cert:3, hp-adult:4
   */
  @Column(name = "bo_use_cert", nullable = false)
//  @ApiModelProperty("general:0, cert:1, adult:2, hp-cert:3, hp-adult:4")
  var boUseCert: Int = 0,

  @Column(name = "bo_use_sns", nullable = false)
  var boUseSns: Int = 0,

  @Column(name = "bo_use_captcha", nullable = false)
  var boUseCaptcha: Int = 0,

  @Column(name = "bo_sort_field")
  var boSortField: String? = null,

  @Column(name = "bo_1_subj")
  var bo1Subj: String? = null,

  @Column(name = "bo_2_subj")
  var bo2Subj: String? = null,

  @Column(name = "bo_3_subj")
  var bo3Subj: String? = null,

  @Column(name = "bo_4_subj")
  var bo4Subj: String? = null,

  @Column(name = "bo_5_subj")
  var bo5Subj: String? = null,

  @Column(name = "bo_6_subj")
  var bo6Subj: String? = null,

  @Column(name = "bo_7_subj")
  var bo7Subj: String? = null,

  @Column(name = "bo_8_subj")
  var bo8Subj: String? = null,

  @Column(name = "bo_9_subj")
  var bo9Subj: String? = null,

  @Column(name = "bo_10_subj")
  var bo10Subj: String? = null,

  @Column(name = "bo_1")
  var bo1: String? = null,

  @Column(name = "bo_2")
  var bo2: String? = null,

  @Column(name = "bo_3")
  var bo3: String? = null,

  @Column(name = "bo_4")
  var bo4: String? = null,

  @Column(name = "bo_5")
  var bo5: String? = null,

  @Column(name = "bo_6")
  var bo6: String? = null,

  @Column(name = "bo_7")
  var bo7: String? = null,

  @Column(name = "bo_8")
  var bo8: String? = null,

  @Column(name = "bo_9")
  var bo9: String? = null,

  @Column(name = "bo_10")
  var bo10: String? = null
) : BaseEntity<BoardDto>() {
  override fun toDto(): BoardDto {
    return BoardDto(
      //ToDo add fields ...
    )
  }
}
