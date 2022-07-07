package com.youngplussoft.admin.server.model

enum class Event(val value: String) {
  CONNECT("connect"),
  CONNECTED("connected"),
  DISCONNECTED("disconnected"),
  CLOSED("disconnected"),
  SUBSCRIBE("subscribe"),
  UNSUBSCRIBE("unsubscribe"),
  HEARTBEAT("heartbeat"),
  PUSH_REQUEST("push-request"),
  PUSH_RESPONSE("push-response"),
  SUCCESS("success"),
  ERROR("error")
}

enum class KafkaEventType(val type: Int) {
  COIN(0),
  COIN_LIST(1),
  COIN_MAP(1)
}

data class Message(
  val accessToken: String?,
  val timestamp: Long,
  val event: String,
  val data: Any
)
