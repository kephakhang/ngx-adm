package com.youngplussoft.admin.common

import com.youngplussoft.admin.extensions.stackTraceString
import mu.KotlinLogging
import java.time.*
import java.time.format.DateTimeFormatter

private val logger = KotlinLogging.logger {}

class DateUtil {
    companion object {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        fun localDatetimeToStr(dateTime: LocalDateTime?): String? {
            //return LocalDateTime.ofInstant(dateTime.toInstant(ZoneOffset.UTC), ZoneOffset.UTC).format(formatter)
            try {
                dateTime?.let {
                    return dateTime?.format(formatter)
                }
                return null
            } catch(ex: Exception) {
                logger.error("parseLocalDatetime : ${ex.stackTraceString}")
                return null
            }
        }

        fun localDateToStr(dateTime: LocalDate?): String? {
            //return LocalDateTime.ofInstant(dateTime.toInstant(ZoneOffset.UTC), ZoneOffset.UTC).format(formatter)
            try {
                dateTime?.let {
                    return dateTime?.format(dateFormatter)
                }
                return null
            } catch(ex: Exception) {
                logger.error("parseLocalDatetime : ${ex.stackTraceString}")
                return null
            }
        }

        fun parseLocalDatetime(str: String?): LocalDateTime? {
            if (str === null || str.isEmpty()) {
                return null
            } else {
                try {
                    val ldt = LocalDateTime.ofInstant(Instant.parse(str), ZoneOffset.UTC)
                    return ldt
                } catch(ex: Exception) {
                    logger.error("parseLocalDatetime : ${ex.stackTraceString}")
                    return null
                }
            }
        }

        fun parseLocalDate(str: String?): LocalDate? {
            if (str === null || str.isEmpty()) {
                return null
            } else {
                try {
                    val ld = LocalDate.ofInstant(Instant.parse(str), ZoneOffset.UTC)
                    return ld
                } catch(ex: Exception) {
                    logger.error("parseLocalDatetime : ${ex.stackTraceString}")
                    return null
                }
            }
        }
    }
}