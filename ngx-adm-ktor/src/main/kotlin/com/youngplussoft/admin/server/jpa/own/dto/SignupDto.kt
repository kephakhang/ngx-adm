package com.youngplussoft.admin.server.jpa.own.dto

data class SignupDto(
  val name: String,
  val email: String,
  val mobile: String,
  val password: String
)

class SignupWrapper(val signup: SignupDto)
