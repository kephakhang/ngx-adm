package com.youngplussoft.admin.server.model

import java.time.Instant

data class KafkaEvent(val type: Int, val timestamp: Instant, val uri: String, val data: PostBody)
