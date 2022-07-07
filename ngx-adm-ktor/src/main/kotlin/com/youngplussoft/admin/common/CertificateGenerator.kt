package com.youngplussoft.admin.common

import java.io.File

object CertificateGenerator {
  @JvmStatic
  fun main(args: Array<String>) {
    val jksFile = File("jks/key.jks").apply {
      parentFile.mkdirs()
    }

    if (!jksFile.exists()) {
      throw Exception("JKS Not Exist Error")
      //generateCertificate(jksFile) // Generates the certificate
    }
  }
}
