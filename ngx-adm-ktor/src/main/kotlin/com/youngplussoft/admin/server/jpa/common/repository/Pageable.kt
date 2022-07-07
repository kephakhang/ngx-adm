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
import java.util.*

/**
 * Abstract interface for pagination information.
 *
 * @author Oliver Gierke
 * @author Mark Paluch
 */
interface Pageable {
  /**
   * Returns whether the current [Pageable] contains pagination information.
   *
   * @return
   */
  val isPaged: Boolean
    get() = true

  /**
   * Returns whether the current [Pageable] does not contain pagination information.
   *
   * @return
   */
  val isUnpaged: Boolean
    get() = !isPaged

  /**
   * Returns the page to be returned.
   *
   * @return the page to be returned.
   */
  val pageNumber: Int

  /**
   * Returns the number of items to be returned.
   *
   * @return the number of items of that page
   */
  val pageSize: Int

  /**
   * Returns the offset to be taken according to the underlying page and page size.
   *
   * @return the offset to be taken
   */
  val offset: Long

  /**
   * Returns the sorting parameters.
   *
   * @return
   */
  val sort: Sort

  /**
   * Returns the current [Sort] or the given one if the current one is unsorted.
   *
   * @param sort must not be null.
   * @return
   */
  fun getSortOr(sort: Sort): Sort {
    assertNotNull("Fallback Sort must not be null!", sort)
    return if (sort.isSorted) sort else sort
  }

  /**
   * Returns the [Pageable] requesting the next [Page].
   *
   * @return
   */
  operator fun next(): Pageable?

  /**
   * Returns the previous [Pageable] or the first [Pageable] if the current one already is the first one.
   *
   * @return
   */
  fun previousOrFirst(): Pageable?

  /**
   * Returns the [Pageable] requesting the first page.
   *
   * @return
   */
  fun first(): Pageable?

  /**
   * Creates a new [Pageable] with `pageNumber` applied.
   *
   * @param pageNumber
   * @return a new [PageRequest].
   * @since 2.5
   */
  fun withPage(pageNumber: Int): Pageable?

  /**
   * Returns whether there's a previous [Pageable] we can access from the current one. Will return
   * false in case the current [Pageable] already refers to the first page.
   *
   * @return
   */
  fun hasPrevious(): Boolean

  /**
   * Returns an [Optional] so that it can easily be mapped on.
   *
   * @return
   */
  fun toOptional(): Optional<Pageable>? {
    return if (isUnpaged) Optional.empty() else Optional.of(this)
  }

  companion object {
    /**
     * Returns a [Pageable] instance representing no pagination setup.
     *
     * @return
     */
    fun unpaged(): Pageable? {
      return Unpaged.INSTANCE
    }

    /**
     * Creates a new [Pageable] for the first page (page number `0`) given `pageSize` .
     *
     * @param pageSize the size of the page to be returned, must be greater than 0.
     * @return a new [Pageable].
     * @since 2.5
     */
    fun ofSize(pageSize: Int): Pageable? {
      return PageRequest.of(0, pageSize)
    }
  }
}
