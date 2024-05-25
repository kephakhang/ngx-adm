package com.youngplussoft.admin.server.jpa.own.entity

import com.youngplussoft.admin.common.DateUtil
import com.youngplussoft.admin.common.LocalDateTimeSerializer
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.own.dto.TenantDto
import com.fasterxml.jackson.annotation.JsonInclude
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "Tenant")
@Table(name = Env.tablePrefix + "tenant")
@JsonInclude(JsonInclude.Include.NON_NULL)
open class Tenant (

  /**
   * 고유 ID(UUID)
   */
//  @ApiModelProperty("고유 UUID")
  @Id
  @Column(name = "id")
  var id: String? = null,

  @Column(name = "name", nullable = false)
  var name: String = "",

  //  @ApiModelProperty("tenant type (0:YoungPlusSoft, 1:OEM, 2:MoldMaker, 3:Supply)")
  @Column(name = "type", nullable = false)
  var type: Int = 0,

  @Column(name = "description", nullable = true)
  var description: String = "",

  @Column(name = "jdbc_host", nullable = true)
  var jdbcHost: String? = null,

  @Column(name = "jdbc_user", nullable = true)
  var jdbcUser: String? = null,

  @Column(name = "jdbc_pass", nullable = true)
  var jdbcPass: String? = null,

  @Column(name = "country_id", nullable = false)
  var countryId: String = "",

  @Column(name = "host_url", nullable = false)
  var hostUrl: String = "",

  @Column(name = "enable", columnDefinition = "BIT", nullable = false)
//  @Type(type = "org.hibernate.type.NumericBooleanType")
  var enable: Boolean = true,

  @Column(name = "prefix", nullable = false)
  var prefix: String = "",

  @Column(name = "hostname", nullable = false)
  var hostname: String = "", // auto genrated hostnam by OEM hostname naming rule


  /**
   * 등록시각
   */
//  @ApiModelProperty("등록시각")
  @CreationTimestamp
  @Convert(converter  = LocalDateTimeSerializer::class)
  @Column(name = "created_at", columnDefinition = "DATETIME", nullable = false, updatable = false)
  var createdAt: LocalDateTime? = null,

  /**
   * 변경시각
   */
//  @ApiModelProperty("수정시각")
  @UpdateTimestamp
  @Convert(converter  = LocalDateTimeSerializer::class)
  @Column(name = "updated_at", columnDefinition = "DATETIME", nullable = false)
  var updatedAt: LocalDateTime? = null,

  /**
   * 삭제시각
   */
//  @ApiModelProperty("수정시각")
  @Convert(converter  = LocalDateTimeSerializer::class)
  @Column(name = "deleted_at", columnDefinition = "DATETIME", nullable = true)
  var deletedAt: LocalDateTime? = null



)  {

  fun toDto(): TenantDto {
    return TenantDto(
      id = id,
      name = name,
      type = type,
      description = description,
      hostUrl = hostUrl,
      prefix = prefix,
      hostname = hostname,
      countryCode = countryId,
      regDatetime =  DateUtil.localDatetimeToStr(createdAt),
      modDatetime = DateUtil.localDatetimeToStr(updatedAt),
      delDatetime = DateUtil.localDatetimeToStr(deletedAt)
    )
  }
}
