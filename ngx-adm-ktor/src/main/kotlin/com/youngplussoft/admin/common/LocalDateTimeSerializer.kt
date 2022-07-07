package com.youngplussoft.admin.common

import java.time.LocalDateTime
import javax.persistence.AttributeConverter
import javax.persistence.Converter
import java.sql.Timestamp;


@Converter(autoApply = true)
class LocalDateTimeSerializer :
  AttributeConverter<LocalDateTime, Timestamp> {
  override fun convertToDatabaseColumn(locDateTime: LocalDateTime?): Timestamp? {
    return if (locDateTime === null) null else Timestamp.valueOf(locDateTime)
  }

  override fun convertToEntityAttribute(sqlTimestamp: Timestamp?): LocalDateTime? {
    return if (sqlTimestamp === null) null else sqlTimestamp.toLocalDateTime()
  }
}
