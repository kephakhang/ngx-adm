package com.youngplussoft.admin.server.jpa.common.entity

//import kotlinx.serialization.Serializable
import com.youngplussoft.admin.common.LocalDateTimeSerializer
import com.youngplussoft.admin.server.jpa.own.dto.UserDto
import kotlinx.serialization.Contextual
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp
import java.io.Serializable
import java.lang.reflect.Modifier
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@MappedSuperclass
abstract class BaseEntity<T> : Serializable {

  /**
   * 고유 ID(UUID)
   */
//  @ApiModelProperty("고유 UUID")
  @Id
  @Column(name = "id")
  var id: String? = null


  /**
   * tenant ID(UUID)
   */
//  @ApiModelProperty("고유 UUID")
  @Column(name = "te_id")
  var teId: String? = null

  /**
   * 등록시각
   */
//  @ApiModelProperty("등록시각")
  @CreationTimestamp
  @Convert(converter  = LocalDateTimeSerializer::class)
  @Column(name = "reg_datetime", columnDefinition = "DATETIME", nullable = false, updatable = false)
  var regDatetime: LocalDateTime? = null

  /**
   * 변경시각
   */
//  @ApiModelProperty("수정시각")
  @UpdateTimestamp
  @Convert(converter  = LocalDateTimeSerializer::class)
  @Column(name = "mod_datetime", columnDefinition = "DATETIME", nullable = false)
  var modDatetime: LocalDateTime? = null

  abstract fun toDto(): T

  override fun equals(other: Any?): Boolean {
    other ?: return false

    if (this === other) return true

    other as BaseEntity<T>

    return (this.id === other.id)
  }

  override fun hashCode(): Int {
    return if (this.id != null) {
      this.id.hashCode()
    } else {
      0
    }
  }

  override fun toString() = "Entity of type ${this.javaClass.name} with id: ${id}:${teId}"

  fun reflectionToString(obj: Any): String {
    val s = LinkedList<String>()
    var clazz: Class<in Any>? = obj.javaClass
    while (clazz != null) {
      for (prop in clazz.declaredFields.filterNot { Modifier.isStatic(it.modifiers) }) {
        prop.isAccessible = true
        s += "${prop.name}=" + prop.get(obj)?.toString()?.trim()
      }
      clazz = clazz.superclass
    }
    return "${obj.javaClass.simpleName}=[${s.joinToString(", ")}]"
  }
}

enum class UserStatus {
  registered, leaved, unconfirmed, dormant, failed, saved, deleted
}

enum class RefundStatus {
  denied, canceled, filled, requested
}
