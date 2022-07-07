package com.youngplussoft.admin.extensions

import java.io.PrintWriter
import java.io.StringWriter

// Extension property on Throwable
val kotlin.Throwable.stackTraceString: String
  get() {
    val stringWriter = StringWriter()
    this.printStackTrace(PrintWriter(stringWriter))
    return stringWriter.toString()
  }
