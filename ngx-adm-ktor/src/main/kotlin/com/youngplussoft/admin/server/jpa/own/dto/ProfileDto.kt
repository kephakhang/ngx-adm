package com.youngplussoft.admin.server.jpa.own.dto

data class ProfileDto(
  val name: String,
  val gender: String?,
  val greeting: String?,
  val image: String?,
  val following: Boolean
)

class ProfileWrapper(val profile: ProfileDto)
