package com.youngplussoft.admin.server.model


data class ConnectionCounts(
  val totalCount: Int,
  val subscriptions: List<SubscriptionCount>
)
