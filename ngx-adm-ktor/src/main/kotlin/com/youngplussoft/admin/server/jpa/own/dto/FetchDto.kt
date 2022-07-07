package com.youngplussoft.admin.server.jpa.own.dto

data class FetchDto(
  val id: String, // id could be mbId, email or mobile
  val password: String
)

class FetchWrapper(val fetch: FetchDto)
