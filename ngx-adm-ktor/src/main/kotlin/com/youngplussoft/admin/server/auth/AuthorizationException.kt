package com.youngplussoft.admin.server.auth

import io.ktor.http.*

class AuthorizationException : Exception() {

    val status = HttpStatusCode.Forbidden

}
