package com.youngplussoft.admin.extensions

import java.security.MessageDigest


fun kotlin.String.md5(): String {
  return this.hashString("MD5")
}

fun kotlin.String.sha256(): String {
  return this.hashString("SHA-256")
}

fun kotlin.String.hashString(algorithm: String): String {
  return MessageDigest
    .getInstance(algorithm)
    .digest(this.toByteArray())
    .fold("", { str, it -> str + "%02x".format(it) })
}

fun kotlin.String.asResource(work: (String) -> Unit) {
  val content = this.javaClass::class.java.getResource(this).readText()
  work(content)
}

fun kotlin.String.isEmail(): Boolean {
  return ("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
      + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
      + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
      + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
      + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
      + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").toRegex().matches(this)
}

fun kotlin.String.isPhoneNumber(): Boolean {
  return "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*\$".toRegex().matches(this)
}
