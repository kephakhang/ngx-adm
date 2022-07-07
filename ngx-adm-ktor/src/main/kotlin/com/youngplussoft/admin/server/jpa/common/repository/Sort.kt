/*
 * Copyright 2008-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.youngplussoft.admin.server.jpa.common.repository


import junit.framework.Assert.assertNotNull
import java.io.Serializable
import java.util.*
import kotlin.streams.toList


/**
 * Sort option for queries. You have to provide at least a list of properties to sort for that must not include
 * null or empty strings. The direction defaults to [Sort.DEFAULT_DIRECTION].
 *
 * @author Oliver Gierke
 * @author Thomas Darimont
 * @author Mark Paluch
 */
open class Sort : Serializable {
  private val orders: List<Order>

  protected constructor(orders: List<Order>) {
    this.orders = orders
  }

  /**
   * Creates a new [Sort] instance.
   *
   * @param direction defaults to [Sort.DEFAULT_DIRECTION] (for null cases, too)
   * @param properties must not be null or contain null or empty strings.
   */
  private constructor(direction: Direction, properties: List<String>?) {
    require(!(properties == null || properties.isEmpty())) { "You have to provide at least one property to sort by!" }
    orders = properties.stream() //
      .map { it: String -> Order(direction, it) }
      .toList()
  }

  /**
   * Returns a new [Sort] with the current setup but descending order direction.
   *
   * @return
   */
  open fun descending(): Sort? {
    return withDirection(Direction.DESC)
  }

  /**
   * Returns a new [Sort] with the current setup but ascending order direction.
   *
   * @return
   */
  open fun ascending(): Sort? {
    return withDirection(Direction.ASC)
  }

  val isSorted: Boolean
    get() = !orders.isEmpty()
  val isUnsorted: Boolean
    get() = !isSorted

  /**
   * Returns a new [Sort] consisting of the [Order]s of the current [Sort] combined with the given
   * ones.
   *
   * @param sort must not be null.
   * @return
   */
  fun and(sort: Sort): Sort {
    assertNotNull("Sort must not be null!", sort)
    val these = ArrayList(orders)
    for (order in sort) {
      these.add(order)
    }
    return by(these)
  }

  /**
   * Returns the order registered for the given property.
   *
   * @param property
   * @return
   */
  fun getOrderFor(property: String): Order? {
    for (order in this) {
      if (order?.property == property) {
        return order
      }
    }
    return null
  }

  /*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
  open operator fun iterator(): Iterator<Order?> {
    return orders.iterator()
  }

  /*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
//  override fun equals(obj: Any?): Boolean {
//    if (this === obj) {
//      return true
//    }
//    if (obj !is Sort) {
//      return false
//    }
//    return orders == obj.orders
//  }

  /*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
//  override fun hashCode(): Int {
//    var result = 17
//    result = 31 * result + orders.hashCode()
//    return result
//  }

  /*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
  override fun toString(): String {
    return if (orders.isEmpty()) "UNSORTED" else orders.toList().joinToString(",")
  }

  /**
   * Creates a new [Sort] with the current setup but the given order direction.
   *
   * @param direction
   * @return
   */
  private fun withDirection(direction: Direction): Sort {
    return by(direction, orders)
  }

  /**
   * Enumeration for sort directions.
   *
   * @author Oliver Gierke
   */
  enum class Direction {
    ASC, DESC;

    /**
     * Returns whether the direction is ascending.
     *
     * @return
     * @since 1.13
     */
    val isAscending: Boolean
      get() = this == ASC

    /**
     * Returns whether the direction is descending.
     *
     * @return
     * @since 1.13
     */
    val isDescending: Boolean
      get() = this == DESC

    companion object {
      /**
       * Returns the [Direction] enum for the given [String] value.
       *
       * @param value
       * @throws IllegalArgumentException in case the given value cannot be parsed into an enum value.
       * @return
       */
      fun fromString(value: String): Direction {
        return try {
          valueOf(value.uppercase(Locale.US))
        } catch (e: Exception) {
          throw IllegalArgumentException(
            String.format(
              "Invalid value '%s' for orders given! Has to be either 'desc' or 'asc' (case insensitive).", value
            ), e
          )
        }
      }

      /**
       * Returns the [Direction] enum for the given [String] or null if it cannot be parsed into an enum
       * value.
       *
       * @param value
       * @return
       */
      fun fromOptionalString(value: String): Optional<Direction> {
        return try {
          Optional.of(fromString(value))
        } catch (e: IllegalArgumentException) {
          Optional.empty()
        }
      }
    }
  }

  /**
   * Enumeration for null handling hints that can be used in [Order] expressions.
   *
   * @author Thomas Darimont
   * @since 1.8
   */
  enum class NullHandling {
    /**
     * Lets the data store decide what to do with nulls.
     */
    NATIVE,

    /**
     * A hint to the used data store to order entries with null values before non null entries.
     */
    NULLS_FIRST,

    /**
     * A hint to the used data store to order entries with null values after non null entries.
     */
    NULLS_LAST
  }

  /**
   * PropertyPath implements the pairing of an [Direction] and a property. It is used to provide input for
   * [Sort]
   *
   * @author Oliver Gierke
   * @author Kevin Raymond
   */
  class Order private constructor(
    direction: Direction?,
    property: String,
    ignoreCase: Boolean,
    nullHandling: NullHandling
  ) : Serializable {
    /**
     * Returns the order the property shall be sorted for.
     *
     * @return
     */
    val direction: Direction

    /**
     * Returns the property to order for.
     *
     * @return
     */
    val property: String

    /**
     * Returns whether or not the sort will be case sensitive.
     *
     * @return
     */
    val isIgnoreCase: Boolean

    /**
     * Returns the used [NullHandling] hint, which can but may not be respected by the used datastore.
     *
     * @return
     * @since 1.7
     */
    val nullHandling: NullHandling

    /**
     * Creates a new [Order] instance. if order is null then order defaults to
     * [Sort.DEFAULT_DIRECTION]
     *
     * @param direction can be null, will default to [Sort.DEFAULT_DIRECTION]
     * @param property must not be null or empty.
     */
    constructor(direction: Direction?, property: String) : this(
      direction,
      property,
      DEFAULT_IGNORE_CASE,
      DEFAULT_NULL_HANDLING
    ) {
    }

    /**
     * Creates a new [Order] instance. if order is null then order defaults to
     * [Sort.DEFAULT_DIRECTION]
     *
     * @param direction can be null, will default to [Sort.DEFAULT_DIRECTION]
     * @param property must not be null or empty.
     * @param nullHandling must not be null.
     */
    constructor(direction: Direction?, property: String, nullHandlingHint: NullHandling) : this(
      direction,
      property,
      DEFAULT_IGNORE_CASE,
      nullHandlingHint
    ) {
    }

    /**
     * Returns whether sorting for this property shall be ascending.
     *
     * @return
     */
    val isAscending: Boolean
      get() = direction.isAscending

    /**
     * Returns whether sorting for this property shall be descending.
     *
     * @return
     * @since 1.13
     */
    val isDescending: Boolean
      get() = direction.isDescending

    /**
     * Returns a new [Order] with the given [Direction].
     *
     * @param direction
     * @return
     */
    fun with(direction: Direction?): Order {
      return Order(direction, property, isIgnoreCase, nullHandling)
    }

    /**
     * Returns a new [Order]
     *
     * @param property must not be null or empty.
     * @return
     * @since 1.13
     */
    fun withProperty(property: String): Order {
      return Order(direction, property, isIgnoreCase, nullHandling)
    }

    /**
     * Returns a new [Sort] instance for the given properties.
     *
     * @param properties
     * @return
     */
    fun withProperties(vararg properties: String): Sort {
      return Sort(direction, properties.toList())
    }

    /**
     * Returns a new [Order] with case insensitive sorting enabled.
     *
     * @return
     */
    fun ignoreCase(): Order {
      return Order(direction, property, true, nullHandling)
    }

    /**
     * Returns a [Order] with the given [NullHandling].
     *
     * @param nullHandling can be null.
     * @return
     * @since 1.8
     */
    fun with(nullHandling: NullHandling): Order {
      return Order(direction, property, isIgnoreCase, nullHandling)
    }

    /**
     * Returns a [Order] with [NullHandling.NULLS_FIRST] as null handling hint.
     *
     * @return
     * @since 1.8
     */
    fun nullsFirst(): Order {
      return with(NullHandling.NULLS_FIRST)
    }

    /**
     * Returns a [Order] with [NullHandling.NULLS_LAST] as null handling hint.
     *
     * @return
     * @since 1.7
     */
    fun nullsLast(): Order {
      return with(NullHandling.NULLS_LAST)
    }

    /**
     * Returns a [Order] with [NullHandling.NATIVE] as null handling hint.
     *
     * @return
     * @since 1.7
     */
    fun nullsNative(): Order {
      return with(NullHandling.NATIVE)
    }

    /*
		 * (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
//    override fun hashCode(): Int {
//      var result = 17
//      result = 31 * result + direction.hashCode()
//      result = 31 * result + property.hashCode()
//      result = 31 * result + if (isIgnoreCase) 1 else 0
//      result = 31 * result + nullHandling.hashCode()
//      return result
//    }

    /*
		 * (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
//    override fun equals(obj: Any?): Boolean {
//      if (this === obj) {
//        return true
//      }
//      if (obj !is Order) {
//        return false
//      }
//      val that = obj
//      return direction == that.direction && property == that.property && isIgnoreCase == that.isIgnoreCase && nullHandling == that.nullHandling
//    }

    /*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
    override fun toString(): String {
      var result = String.format("%s: %s", property, direction)
      if (NullHandling.NATIVE != nullHandling) {
        result += ", $nullHandling"
      }
      if (isIgnoreCase) {
        result += ", ignoring case"
      }
      return result
    }

    /**
     * Creates a new [Order] instance. if order is null then order defaults to
     * [Sort.DEFAULT_DIRECTION]
     *
     * @param direction can be null, will default to [Sort.DEFAULT_DIRECTION]
     * @param property must not be null or empty.
     * @param ignoreCase true if sorting should be case insensitive. false if sorting should be case sensitive.
     * @param nullHandling must not be null.
     * @since 1.7
     */
    init {
      assert(!property.isNullOrEmpty()) { "Property must not null or empty!" }
      this.direction = direction ?: DEFAULT_DIRECTION
      this.property = property
      isIgnoreCase = ignoreCase
      this.nullHandling = nullHandling
    }
  }

  companion object {
    private const val serialVersionUID = 1522511010900108987L
    private val DEFAULT_DIRECTION = Direction.ASC
    private const val DEFAULT_IGNORE_CASE = false
    private val DEFAULT_NULL_HANDLING = NullHandling.NATIVE

    /**
     * Creates a new [Order] instance. Takes a single property. Direction defaults to
     * [Sort.DEFAULT_DIRECTION].
     *
     * @param property must not be null or empty.
     * @since 2.0
     */
    fun by(vararg properties: Any): Sort {
      return if (properties.size == 0) {
        Sort(DEFAULT_DIRECTION, listOf())
      } else {
        when (properties[0]) {
          is String -> Sort(properties.map { it -> Order(DEFAULT_DIRECTION, it as String) }.toList())
          is Order -> Sort(properties.toList() as List<Order>)
          is Direction -> {
            if (properties.size == 1) {
              Sort(properties[0] as Direction, listOf())
            }
            when (properties[1]) {
              is String -> Sort((1..properties.size - 1).map { it ->
                Order(properties[0] as Direction, properties[it] as String)
              }.toList())
              is Order -> Sort((1..properties.size - 1).map { it ->
                Order(properties[0] as Direction, (properties[it] as Order).property)
              }.toList())
              else -> throw UnsupportedOperationException("Unknown foramt sort information")
            }
          }
          else -> throw UnsupportedOperationException("Unknown foramt sort information")
        }
      }
    }

    /**
     * Creates a new [Order] instance. Takes a single property. Direction is [Direction.ASC] and
     * NullHandling [NullHandling.NATIVE].
     *
     * @param property must not be null or empty.
     * @since 2.0
     */
    fun asc(property: String): Order {
      return Order(Direction.ASC, property, DEFAULT_NULL_HANDLING)
    }

    /**
     * Creates a new [Order] instance. Takes a single property. Direction is [Direction.DESC] and
     * NullHandling [NullHandling.NATIVE].
     *
     * @param property must not be null or empty.
     * @since 2.0
     */
    fun desc(property: String): Order {
      return Order(Direction.DESC, property, DEFAULT_NULL_HANDLING)
    }
  }

  /**
   * Extension of Sort to use method handles to define properties to sort by.
   *
   * @author Oliver Gierke
   * @since 2.2
   * @soundtrack The Intersphere - Linger (The Grand Delusion)
   * /
  class TypedSort<T> private constructor(recorded: Recorded<T>) : Sort(emptyList()) {
  private val recorded: Recorded<T>

  private constructor(type: Class<T>) : this(MethodInvocationRecorder.forProxyOf(type)) {}

  fun <S> by(property: Function<T, S>?): TypedSort<S> {
  return TypedSort<Any?>(recorded.record(property))
  }

  fun <S> by(collectionProperty: Recorded.ToCollectionConverter<T, S>?): TypedSort<S> {
  return TypedSort<Any?>(recorded.record(collectionProperty))
  }

  fun <S> by(mapProperty: Recorded.ToMapConverter<T, S>?): TypedSort<S> {
  return TypedSort<Any?>(recorded.record(mapProperty))
  }

  override fun ascending(): Sort? {
  return withDirection { obj: Sort -> obj.ascending() }
  }

  override fun descending(): Sort? {
  return withDirection { obj: Sort -> obj.descending() }
  }

  private fun withDirection(direction: Function<Sort, Sort?>): Sort {
  return recorded.getPropertyPath() //
  .map(Sort::by) //
  .map(direction) //
  .orElseGet { unsorted() }
  }

  /*
   * (non-Javadoc)
   * @see org.springframework.data.domain.Sort#iterator()
  */
  override fun iterator(): Iterator<Order?> {
  return recorded.getPropertyPath() //
  .map { property: String -> Order.by(property) } //
  .map { o: T -> setOf(o) } //
  .orElseGet { emptySet() }.iterator()
  }

  /*
   * (non-Javadoc)
   * @see org.springframework.data.domain.Sort#toString()
  */
  override fun toString(): String {
  return recorded.getPropertyPath() //
  .map(Sort::by) //
  .orElseGet { unsorted() } //
  .toString()
  }

  companion object {
  private const val serialVersionUID = -3550403511206745880L
  }

  init {
  this.recorded = recorded
  }
  }

  companion object {
  private const val serialVersionUID = 5737186511678863905L
  private val UNSORTED = by(listOf())
  val DEFAULT_DIRECTION = Direction.ASC

  /**
   * Creates a new [Sort] for the given properties.
   *
   * @param properties must not be null.
   * @return
  */
  fun by(vararg properties: String): Sort {
  assertNotNull("Properties must not be null!", properties)
  return if (properties.size == 0 //
  ) unsorted() //
  else Sort(DEFAULT_DIRECTION, properties.toList())
  }

  /**
   * Creates a new [Sort] for the given [Order]s.
   *
   * @param orders must not be null.
   * @return
  */
  fun by(orders: List<Order>): Sort {
  assertNotNull("Orders must not be null!", orders)
  return if (orders.isEmpty()) unsorted() else Sort(orders)
  }

  /**
   * Creates a new [Sort] for the given [Order]s.
   *
   * @param orders must not be null.
   * @return
  */
  fun by(vararg orders: Order): Sort {
  assertNotNull("Orders must not be null!", orders)
  return Sort(orders.toList())
  }

  /**
   * Creates a new [Sort] for the given [Order]s.
   *
   * @param direction must not be null.
   * @param properties must not be null.
   * @return
  */
  fun by(direction: Direction?, vararg properties: String): Sort {
  assertNotNull("Direction must not be null!", direction)
  assertNotNull("Properties must not be null!", properties)
  assert(properties.size > 0) {"At least one property must be given!"}
  return Sort.by(
  Arrays.stream(properties) //
  .map { it: String -> Order(direction, it) } //
  .toList())
  }

  /**
   * Creates a new [TypedSort] for the given type.
   *
   * @param type must not be null.
   * @return
   * @since 2.2
  */
  fun <T> sort(type: Class<T>?): TypedSort<T> {
  return TypedSort<T>(type)
  }

  /**
   * Returns a [Sort] instances representing no sorting setup at all.
   *
   * @return
  */
  fun unsorted(): Sort {
  return UNSORTED
  }
  }
   */
}
