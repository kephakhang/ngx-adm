package com.youngplussoft.admin.server.model

enum class IntegrationType(val value: String, val no: Int) {
  TEST("test", 0),
  AI("ai", 1),
  SIMULATOR("simulator", 2),
  TERMINAL("terminal", 3)
}

data class PostBody(
  val requestId: String,
  val tenantId: String,
  val type: String,
  val data: LinkedHashMap<String, Any>
)
