package com.youngplussoft.admin.server.jpa.own.entity

import com.youngplussoft.admin.common.DateUtil
import com.fasterxml.jackson.annotation.JsonInclude
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.common.entity.BaseEntity
import com.youngplussoft.admin.server.jpa.own.dto.GroupDto
import com.youngplussoft.admin.server.jpa.own.dto.UserDto
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table


@Entity(name = "Group")
@Table(name = Env.tablePrefix + "group")
@JsonInclude(JsonInclude.Include.NON_NULL)
open class Group(

  @Column(name = "gr_name", nullable = false)
  var grName: String = "",

  @Column(name = "gr_subject", nullable = false)
  var grSubject: String = "",

//  @ApiModelProperty("0:pc, 1:mobile, 2:both")
  @Column(name = "gr_device", nullable = false)
  var grDevice: Int = 2,

  @Column(name = "gr_admin")
  var grAdmin: String? = null,

  @Column(name = "gr_use_access", nullable = false)
  var grUseAccess: Int = 0,

  @Column(name = "gr_order", nullable = false)
  var grOrder: Int = 0,

  @Column(name = "gr_1_subj")
  var gr1Subj: String? = null,

  @Column(name = "gr_2_subj")
  var gr2Subj: String? = null,

  @Column(name = "gr_3_subj")
  var gr3Subj: String? = null,

  @Column(name = "gr_4_subj")
  var gr4Subj: String? = null,

  @Column(name = "gr_5_subj")
  var gr5Subj: String? = null,

  @Column(name = "gr_6_subj")
  var gr6Subj: String? = null,

  @Column(name = "gr_7_subj")
  var gr7Subj: String? = null,

  @Column(name = "gr_8_subj")
  var gr8Subj: String? = null,

  @Column(name = "gr_9_subj")
  var gr9Subj: String? = null,

  @Column(name = "gr_10_subj")
  var gr10Subj: String? = null,

  @Column(name = "gr_1")
  var gr1: String? = null,

  @Column(name = "gr_2")
  var gr2: String? = null,

  @Column(name = "gr_3")
  var gr3: String? = null,

  @Column(name = "gr_4")
  var gr4: String? = null,

  @Column(name = "gr_5")
  var gr5: String? = null,

  @Column(name = "gr_6")
  var gr6: String? = null,

  @Column(name = "gr_7")
  var gr7: String? = null,

  @Column(name = "gr_8")
  var gr8: String? = null,

  @Column(name = "gr_9")
  var gr9: String? = null,

  @Column(name = "gr_10")
  var gr10: String? = null

) : BaseEntity<GroupDto>() {
  override fun toDto(): GroupDto {
    return GroupDto(
      id = id!!,
      tenantId = teId!!,
      // ToDo add fields
      regDatetime = DateUtil.localDatetimeToStr(regDatetime),
      modDatetime = DateUtil.localDatetimeToStr(modDatetime)
    )
  }
}
