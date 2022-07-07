package com.youngplussoft.admin.exception

import mu.KotlinLogging
import kotlin.collections.HashMap

val logger = KotlinLogging.logger {}

class YpsError(
    val code: String,
    val status: Int,
    var description: String,
    val message: HashMap<String, String>,
    val origin: String
)
