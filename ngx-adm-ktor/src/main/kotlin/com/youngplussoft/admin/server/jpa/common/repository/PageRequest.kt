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


/**
 * Basic Java Bean implementation of `Pageable`.
 *
 * @author Oliver Gierke
 * @author Thomas Darimont
 * @author Anastasiia Smirnova
 * @author Mark Paluch
 */
class PageRequest(page: Int, size: Int, sort: Sort) : AbstractPageRequest(page, size) {
  /*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#getSort()
	 */  override val sort: Sort

  /*
 * (non-Javadoc)
 * @see org.springframework.data.domain.Pageable#next()
 */
  override fun next(): Pageable? {
    return PageRequest(pageNumber + 1, pageSize, sort)
  }

  /*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.AbstractPageRequest#previous()
	 */
  override fun previous(): PageRequest? {
    return if (pageNumber == 0) this else PageRequest(pageNumber - 1, pageSize, sort)
  }

  /*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#first()
	 */
  override fun first(): Pageable? {
    return PageRequest(0, pageSize, sort)
  }

  /*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
  override fun equals(obj: Any?): Boolean {
    if (this === obj) {
      return true
    }
    if (obj !is PageRequest) {
      return false
    }
    val that = obj
    return super.equals(that) && sort.equals(that.sort)
  }

  /**
   * Creates a new [PageRequest] with `pageNumber` applied.
   *
   * @param pageNumber
   * @return a new [PageRequest].
   * @since 2.5
   */
  override fun withPage(pageNumber: Int): PageRequest? {
    return PageRequest(pageNumber, pageSize, sort)
  }

  /**
   * Creates a new [PageRequest] with [Direction] and `properties` applied.
   *
   * @param direction must not be null.
   * @param properties must not be null.
   * @return a new [PageRequest].
   * @since 2.5
   */
  fun withSort(direction: Sort.Direction, vararg properties: String?): PageRequest {
    return PageRequest(pageNumber, pageSize, Sort.by(direction, properties))
  }

  /**
   * Creates a new [PageRequest] with [Sort] applied.
   *
   * @param sort must not be null.
   * @return a new [PageRequest].
   * @since 2.5
   */
  fun withSort(sort: Sort): PageRequest {
    return PageRequest(pageNumber, pageSize, sort)
  }

  /*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
  override fun hashCode(): Int {
    return 31 * super.hashCode() + sort.hashCode()
  }

  /*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
  override fun toString(): String {
    return String.format("Page request [number: %d, size %d, sort: %s]", pageNumber, pageSize, sort)
  }

  companion object {
    private const val serialVersionUID = -4541509938956089562L

    /**
     * Creates a new unsorted [PageRequest].
     *
     * @param page zero-based page index, must not be negative.
     * @param size the size of the page to be returned, must be greater than 0.
     * @since 2.0
     */
    fun of(page: Int, size: Int): PageRequest {
      return of(page, size, Sort.by())
    }

    /**
     * Creates a new [PageRequest] with sort parameters applied.
     *
     * @param page zero-based page index.
     * @param size the size of the page to be returned.
     * @param sort must not be null, use [Sort.unsorted] instead.
     * @since 2.0
     */
    fun of(page: Int, size: Int, sort: Sort): PageRequest {
      return PageRequest(page, size, sort)
    }

    /**
     * Creates a new [PageRequest] with sort direction and properties applied.
     *
     * @param page zero-based page index, must not be negative.
     * @param size the size of the page to be returned, must be greater than 0.
     * @param direction must not be null.
     * @param properties must not be null.
     * @since 2.0
     */
    fun of(page: Int, size: Int, direction: Sort.Direction, vararg properties: String?): PageRequest {
      return of(page, size, Sort.by(direction, properties))
    }

    /**
     * Creates a new [PageRequest] for the first page (page number `0`) given `pageSize` .
     *
     * @param pageSize the size of the page to be returned, must be greater than 0.
     * @return a new [PageRequest].
     * @since 2.5
     */
    fun ofSize(pageSize: Int): PageRequest {
      return of(0, pageSize)
    }
  }

  /**
   * Creates a new [PageRequest] with sort parameters applied.
   *
   * @param page zero-based page index, must not be negative.
   * @param size the size of the page to be returned, must be greater than 0.
   * @param sort must not be null, use [Sort.unsorted] instead.
   */
  init {
    assertNotNull("Sort must not be null!", sort)
    this.sort = sort
  }
}
