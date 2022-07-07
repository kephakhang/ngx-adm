/*
 * Copyright 2017-2021 the original author or authors.
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

/**
 * [Pageable] implementation to represent the absence of pagination information.
 *
 * @author Oliver Gierke
 */
internal enum class Unpaged : Pageable {
  INSTANCE;

  override val sort: Sort
    get() = Sort.by()

  /*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#isPaged()
	 */
  override val isPaged: Boolean
    get() = false

  /*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#previousOrFirst()
	 */
  override fun previousOrFirst(): Pageable? {
    return this
  }

  /*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#next()
	 */
  override fun next(): Pageable? {
    return this
  }

  /*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#hasPrevious()
	 */
  override fun hasPrevious(): Boolean {
    return false
  }

  /*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#getPageSize()
	 */
  override val pageSize: Int
    get() {
      throw UnsupportedOperationException()
    }

  /*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#getPageNumber()
	 */
  override val pageNumber: Int
    get() {
      throw UnsupportedOperationException()
    }

  /*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#getOffset()
	 */
  override val offset: Long
    get() {
      throw UnsupportedOperationException()
    }

  /*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#first()
	 */
  override fun first(): Pageable? {
    return this
  }

  /*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#withPage(int)
	 */
  override fun withPage(pageNumber: Int): Pageable? {
    if (pageNumber == 0) {
      return this
    }
    throw UnsupportedOperationException()
  }
}
