package com.youngplussoft.admin.server.jpa.own.enum

enum class AdminStatus(val value: String, val no: Int) {
  BLOCKED("blocked", -2), // blocked user added to blacklist
  DORMANT("dormant", -1), // dormant user by long time disuse
  WAIT("wait", 0), // wait for certification of email or mobile
  CERTIFIED("certified", 2)
}
