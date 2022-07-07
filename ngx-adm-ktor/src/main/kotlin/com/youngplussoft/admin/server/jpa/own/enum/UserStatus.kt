package com.youngplussoft.admin.server.jpa.own.enum

enum class UserStatus(val value: String, val no: Int) {
  BLOCKED("blocked", -2), // blocked user added to blacklist
  DORMANT("dormant", -1), // dormant user by long time disuse
  WAIT("wait", 0), // wait for certification of email or mobile
  EMAIL_ONLY("email_only_certified", 1),
  MOBILE_ONLY("mibile_only_certified", 2),
  BOTH("both", 3)
}
