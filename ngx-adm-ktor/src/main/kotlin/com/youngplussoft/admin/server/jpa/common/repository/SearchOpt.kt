package com.youngplussoft.admin.server.jpa.common.repository

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
class SearchOpt {
  var id: Long? = null
  var page: Int? = 0
  var size: Long = 20
  var search: String? = null
  val align: String? = null
  val orderColumn: String? = null
  val orderAsc: String? = null
  val filter: List<String>? = null
  val startDateTime: LocalDateTime? = null
  val endDateTime: LocalDateTime? = null

  val skipCount: Long
    get() = if (page == null) 0 else (page!! - 1) * size

  companion object {
    const val serialVersionUID = -1143424526957088609L
  }
}
