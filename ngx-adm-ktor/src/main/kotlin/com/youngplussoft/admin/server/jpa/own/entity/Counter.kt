package com.youngplussoft.admin.server.jpa.own.entity

import com.youngplussoft.admin.common.DateUtil
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.common.entity.BaseEntity
import com.youngplussoft.admin.server.jpa.own.dto.CounterDto
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import javax.persistence.*

@Entity(name = "Counter")
@Table(name = Env.tablePrefix + "counter")
@JsonInclude(JsonInclude.Include.NON_NULL)
open class Counter (

  //Counter.Id should be 13 bytes : H/W Specification

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tr_id", insertable = false, updatable = false)
  var terminal: Terminal? = null,


  @Column(name = "tr_id", nullable = false)
  var trId: String? = "",


  @Column(name = "co_version", nullable = false)
  var coVersion: Int = 3,

  //  @ApiModelProperty("terminal type (0:available, 1:installed")
  @Column(name = "co_status", nullable = false)
  var coStatus: Int = 0,

): BaseEntity<CounterDto>()  {

  override fun toDto(): CounterDto {
    return CounterDto(
      id = id,
      tenantId = teId,
      terminalId = trId,
      version = coVersion,
      status = coStatus,
      regDatetime =  DateUtil.localDatetimeToStr(createdAt),
      modDatetime = DateUtil.localDatetimeToStr(updatedAt),
      delDatetime = DateUtil.localDatetimeToStr(deletedAt)
    )
  }
}
