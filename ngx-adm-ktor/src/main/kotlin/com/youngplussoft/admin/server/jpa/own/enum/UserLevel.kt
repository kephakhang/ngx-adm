package com.youngplussoft.admin.server.jpa.own.enum

enum class UserLevel(val value: String, val no: Int) {
  NONE("none", 0), // not user
  GUEST("guest", 1),  // guest user who waits for permission
  FACTORY("factory", 10),  // Factory Admin who can register new counter
  COMPANY("company", 20),
  COMPANY_USER("company_user", 30),
  AGENT("agent", 40),
  AGENT_USER("agent_user", 50),
  YoungPlusSoft("YoungPlusSoft", 100), // YoungPlusSoft Admin who can register accounts to OEM tenant
  ADMIN("admin", 1000)
}
