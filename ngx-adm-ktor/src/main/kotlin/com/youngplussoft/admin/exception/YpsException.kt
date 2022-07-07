package com.youngplussoft.admin.exception

import com.youngplussoft.admin.server.jpa.own.dto.UserDto
import io.ktor.server.application.*
import io.ktor.server.request.*
import org.eclipse.jetty.http.HttpStatus


open class YpsException(val code: ErrorCode, cause: Throwable?=null, status: Int?=null, vararg args: String?) : Exception(cause){
  val httpStatus = status
  val argList = ArrayList<String>()

  init {
    if (args.size > 0) {
      for (x in args) argList.add(x as String)
    }
  }

}

class YpsBadRequestException(cause: Throwable?, vararg args: String?): YpsException(ErrorCode.E00001, cause, HttpStatus.BAD_REQUEST_400, *args)

class YpsNotFoundException(cause: Throwable?, vararg args: String?): YpsException(ErrorCode.E00002, cause, HttpStatus.NOT_FOUND_404, *args)
class YpsSessionNotFoundException(cause: Throwable?, vararg args: String?): YpsException(ErrorCode.E00003, cause, HttpStatus.UNAUTHORIZED_401, *args)

class YpsForbiddenException(session: UserDto, call: ApplicationCall): YpsException(ErrorCode.E00004, null, HttpStatus.FORBIDDEN_403, session.name, call.request.uri, call.request.httpMethod.value)
