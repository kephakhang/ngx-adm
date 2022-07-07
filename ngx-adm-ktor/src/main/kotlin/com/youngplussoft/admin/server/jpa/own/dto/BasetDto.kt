package com.youngplussoft.admin.server.jpa.own.dto

abstract class BasetDto<T>() {
    abstract fun toEntity(): T
}