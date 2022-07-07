package com.youngplussoft.admin.server.jpa.own.dto

data class ConfirmDto(
  val uid: String, // id could be mbId, email or mobile
  val confirm: String
)

class ConfirmDtoWrapper(val confirm: ConfirmDto)
