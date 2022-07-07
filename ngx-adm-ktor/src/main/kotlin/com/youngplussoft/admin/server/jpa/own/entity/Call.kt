package com.youngplussoft.admin.server.jpa.own.entity

import com.youngplussoft.admin.common.DateUtil
import com.youngplussoft.admin.server.env.Env
import com.youngplussoft.admin.server.jpa.common.entity.BaseEntity
import com.youngplussoft.admin.server.jpa.own.dto.CallDto
import com.youngplussoft.admin.server.model.PostBody
import com.fasterxml.jackson.annotation.JsonInclude
import javax.persistence.*

@Entity(name = "Call")
@Table(name = Env.tablePrefix + "call")
@JsonInclude(JsonInclude.Include.NON_NULL)
open class Call (

  @Column(name = "ca_request_id", nullable = false)
  var caRequestId: String = "",

  @Column(name = "ca_uri", nullable = false)
  var caUri: String = "",

  @Column(name = "ca_request_url", nullable = false)
  var caRequestUrl: String = "",

  @Column(name = "ca_method", nullable = false)
  var caMethod: String = "",

  @Lob
  @Column(name = "ca_request_body", columnDefinition = "text")
  var caRequestBody: String,

  @Lob
  @Column(name = "ca_response_body", columnDefinition = "text")
  var caResponseBody: String

  )  : BaseEntity<CallDto>() {

  override fun toDto(): CallDto {
    return CallDto(
      id = id,
      tenantId = teId,
      requestId = caRequestId,
      uri = caUri,
      requestUrl = caRequestUrl,
      method = caMethod,
      requestBody = Env.gson.fromJson(caRequestBody, PostBody::class.java),
      responseBody = Env.gson.fromJson(caResponseBody, PostBody::class.java),
      regDatetime =  DateUtil.localDatetimeToStr(regDatetime),
      modDatetime = DateUtil.localDatetimeToStr(modDatetime)
    )
  }
}
