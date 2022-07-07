package com.youngplussoft.admin.server.model

import java.math.BigDecimal

data class Ticker(
  val channel: String,
  val currency_pair: String,
  val timestamp: Long,
  val last: BigDecimal,
  val open: BigDecimal,
  val bid: BigDecimal,
  val ask: BigDecimal,
  val low: BigDecimal,
  val high: BigDecimal,
  val volume: BigDecimal,
  val change: BigDecimal
)
