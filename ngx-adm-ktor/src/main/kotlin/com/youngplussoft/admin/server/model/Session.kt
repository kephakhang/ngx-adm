package com.youngplussoft.admin.server.model

import io.ktor.server.websocket.*

data class Session<Any>(val socketSession: WebSocketServerSession, val session: Any)
