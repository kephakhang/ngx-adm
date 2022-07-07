package com.youngplussoft.admin.server.jpa.own.entity

import com.youngplussoft.admin.common.DateUtil
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.common.entity.BaseEntity
import com.youngplussoft.admin.server.jpa.own.dto.TerminalDto
import com.fasterxml.jackson.annotation.JsonInclude
import javax.persistence.*

@Entity(name = "Terminal")
@Table(name = Env.tablePrefix + "terminal")
@JsonInclude(JsonInclude.Include.NON_NULL)
open class Terminal (

  //Terimainl.Id should be lower than 16 bytes : H/W Team suggestion

  @Column(name = "tr_version", nullable = false)
  var trVersion: Int = 3,

  //  @ApiModelProperty("terminal type (0:available, 1:installed)")
  @Column(name = "tr_status", nullable = false)
  var trStatus: Int = 0,

  @Column(name = "tr_ip", nullable = true)
  var trIp: String? = null,

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "terminal", cascade = [CascadeType.ALL])
  var counterList: MutableList<Counter> = listOf<Counter>().toMutableList()


): BaseEntity<TerminalDto>()  {

  override fun toDto(): TerminalDto {
    return TerminalDto(
      id = id,
      tenantId = teId,
      version = trVersion,
      status = trStatus,
      ip = trIp,
      regDatetime =  DateUtil.localDatetimeToStr(regDatetime),
      modDatetime = DateUtil.localDatetimeToStr(modDatetime),
      counterList = counterList.map{it -> it.toDto()}.toMutableList()
    )
  }
}
