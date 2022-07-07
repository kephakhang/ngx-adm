package com.youngplussoft.admin.server.jpa.own.dto

data class LoginDto(
  val id: String, // id could be mbId, email or mobile
  val password: String
)

class LoginWrapper(val login: LoginDto)
