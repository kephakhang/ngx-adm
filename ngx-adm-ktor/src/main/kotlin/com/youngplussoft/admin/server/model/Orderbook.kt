package com.youngplussoft.admin.server.model

data class Orderbook(
  val channel: String,
  val currency_pair: String,
  val timestamp: Long,
  val bids: Array<Order>,
  val asks: Array<Order>
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Orderbook

    if (channel != other.channel) return false
    if (currency_pair != other.currency_pair) return false
    if (timestamp != other.timestamp) return false
    if (!bids.contentEquals(other.bids)) return false
    if (!asks.contentEquals(other.asks)) return false

    return true
  }

  override fun hashCode(): Int {
    var result = channel.hashCode()
    result = 31 * result + currency_pair.hashCode()
    result = 31 * result + timestamp.hashCode()
    result = 31 * result + bids.contentHashCode()
    result = 31 * result + asks.contentHashCode()
    return result
  }
}
