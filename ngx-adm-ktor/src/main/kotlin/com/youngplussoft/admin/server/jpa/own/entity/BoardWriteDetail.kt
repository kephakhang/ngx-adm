package com.youngplussoft.admin.server.jpa.own.entity


import com.youngplussoft.admin.server.env.Env
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable
import javax.persistence.*


@Entity(name = "BoardWriteDetail")
@Table(name = Env.tablePrefix + "board_write_detail")
@JsonInclude(JsonInclude.Include.NON_NULL)
open class BoardWriteDetail(

  @JsonIgnore
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "wr_id", insertable = false, updatable = false)
  val boardWrite: BoardWrite? = null,

  @Id
  @Column(name = "wr_id", updatable = false)
  private val wrId: String? = null,

  @Column(name = "wr_content", columnDefinition = "text", nullable = false)
  private val wrContent: String = "",

  @Column(name = "wr_link1", columnDefinition = "text")
  private val wrLink1: String? = null,

  @Column(name = "wr_link2", columnDefinition = "text")
  private val wrLink2: String? = null,

  @Column(name = "wr_link1_hit")
  private val wrLink1Hit: Int? = null,

  @Column(name = "wr_link2_hit")
  private val wrLink2Hit: Int? = null,

  @Column(name = "wr_password")
  private val wrPassword: String? = null,

  @Column(name = "wr_email")
  private val wrEmail: String? = null,

  @Column(name = "wr_homepage")
  private val wrHomepage: String? = null,

  @Column(name = "wr_ip")
  private val wrIp: String? = null,

  @Column(name = "wr_facebook_user")
  private val wrFacebookUser: String? = null,

  @Column(name = "wr_twitter_user")
  private val wrTwitterUser: String? = null,

  @Column(name = "wr_1")
  private val wr1: String? = null,

  @Column(name = "wr_2")
  private val wr2: String? = null,

  @Column(name = "wr_3")
  private val wr3: String? = null,

  @Column(name = "wr_4")
  private val wr4: String? = null,

  @Column(name = "wr_5")
  private val wr5: String? = null,

  @Column(name = "wr_6")
  private val wr6: String? = null,

  @Column(name = "wr_7")
  private val wr7: String? = null,

  @Column(name = "wr_8")
  private val wr8: String? = null,

  @Column(name = "wr_9")
  private val wr9: String? = null,

  @Column(name = "wr_10")
  private val wr10: String? = null
) : Serializable
