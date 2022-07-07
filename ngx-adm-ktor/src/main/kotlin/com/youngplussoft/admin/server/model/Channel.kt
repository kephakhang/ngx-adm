package com.youngplussoft.admin.server.model

import java.util.*

enum class Channel(val value: String, val no: Int) {
  TEST("test", 0),
  TICKER("ticker", 1),
  ORDERBOOK("orderbook", 2),
  TRANSACTION("transaction", 3),
  MAGIC_NANNY("magicnanny", 4)
}

//channel: ["ticker", "orderbook:btc_krw,eth_krw"]
typealias ChannelList = TreeMap<String, Any>
