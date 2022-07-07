package com.youngplussoft.admin.server.jpa.own.enum

enum class AiCall(val value: String, val no: Int) {
  LAUNCH("/launch", 1),
  FETCH_DATA("/fetchData", 2),
  CALLBACK("/callback", 3),
  RESULTS("/results", 4),
}
