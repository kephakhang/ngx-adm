package com.youngplussoft.admin.common

import java.math.BigDecimal
import javax.persistence.AttributeConverter
import javax.persistence.Converter


@Converter
class BigDecimalSerializer : AttributeConverter<BigDecimal, Double> {
  open override fun convertToDatabaseColumn(attribute: BigDecimal?): Double? {
    return attribute?.toDouble()
  }

  open override fun convertToEntityAttribute(dbData: Double?): BigDecimal? {
      return dbData?.toBigDecimal()
  }
}
