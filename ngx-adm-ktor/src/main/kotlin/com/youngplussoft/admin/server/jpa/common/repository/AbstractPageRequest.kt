/*
 * Copyright 2013-2021 the original author or authors.
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

import java.io.Serializable

/**
 * Abstract Java Bean implementation of `Pageable`.
 *
 * @author Thomas Darimont
 * @author Oliver Gierke
 * @author Alex Bondarev
 */
abstract class AbstractPageRequest(page: Int, size: Int) : Pageable, Serializable {
  /*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#getPageNumber()
	 */  override val pageNumber: Int

  /*
  * (non-Javadoc)
  * @see org.springframework.data.domain.Pageable#getPageSize()
  */  override val pageSize: Int

  /*
 * (non-Javadoc)
 * @see org.springframework.data.domain.Pageable#getOffset()
 */
  override val offset: Long
    get() = pageNumber.toLong() * pageSize.toLong()

  /*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#hasPrevious()
	 */
  override fun hasPrevious(): Boolean {
    return pageNumber > 0
  }

  /*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#previousOrFirst()
	 */
  override fun previousOrFirst(): Pageable? {
    return if (hasPrevious()) previous() else first()
  }

  /*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#next()
	 */
  abstract override fun next(): Pageable?

  /**
   * Returns the [Pageable] requesting the previous [Page].
   *
   * @return
   */
  abstract fun previous(): Pageable?

  /*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#first()
	 */
  abstract override fun first(): Pageable?

  /*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
//  override fun hashCode(): Int {
//    val prime = 31
//    var result = 1
//    result = prime * result + pageNumber
//    result = prime * result + pageSize
//    return result
//  }

  /*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
//  override fun equals(obj: Any?): Boolean {
//    if (this === obj) {
//      return true
//    }
//    if (obj == null || javaClass != obj.javaClass) {
//      return false
//    }
//    val other = obj as AbstractPageRequest
//    return pageNumber == other.pageNumber && pageSize == other.pageSize
//  }
//
//  companion object {
//    private const val serialVersionUID = 1232825578694716871L
//  }

  /**
   * Creates a new [AbstractPageRequest]. Pages are zero indexed, thus providing 0 for `page` will return
   * the first page.
   *
   * @param page must not be less than zero.
   * @param size must not be less than one.
   */
  init {
    require(page >= 0) { "Page index must not be less than zero!" }
    require(size >= 1) { "Page size must not be less than one!" }
    pageNumber = page
    pageSize = size
  }
}
