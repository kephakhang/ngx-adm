package com.youngplussoft.admin.common


import java.io.IOException
import java.net.NetworkInterface
import java.util.*
import java.util.concurrent.TimeUnit


internal object ComputerIdentifier {
  fun generateHostId(): String? {

    return UUID.randomUUID().toString()

//        val hostname =  runCommand("hostname")
//        if( hostname == null ) {
//            return UUID.randomUUID().toString()
//        } else {
//            return hostname
//        }

    /*
    val dockerContainerId = runCommand("cat /proc/self/cgroup | head -1 | tr --delete ‘9:pref_event:/docker/’")
    if( dockerContainerId == null || dockerContainerId.trim().isEmpty() ) {
        val nicAddr = null //getNicAddress()
        if( nicAddr == null ) {
            val hostId = ComputerIdentifier.runCommand("hostid")
            if (hostId == null) {
                val hostname =  runCommand("hostname")
                if( hostname == null ) {
                    return UUID.randomUUID().toString()
                } else {
                    return hostname
                }
            } else {
                return hostId
            }
        } else {
            return nicAddr
        }
    } else {
        return dockerContainerId
    }
    */

  }

  fun runCommand(cmd: String): String? {
    try {
      val parts = cmd.split("\\s".toRegex())
      val proc = ProcessBuilder(*parts.toTypedArray())
        //.directory(File("."))
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectError(ProcessBuilder.Redirect.PIPE)
        .start()

      proc.waitFor(60, TimeUnit.MINUTES)
      if (proc.exitValue() == 0)
        return proc.inputStream.bufferedReader().readText()
      else
        return null
    } catch (e: IOException) {
      e.printStackTrace()
      return null
    }
  }

  fun getNicAddress(): String? {
    try {
      val nets = NetworkInterface.getNetworkInterfaces();
      for (netint in nets.toList()) {
        if (netint.getName().startsWith("lo") || netint.getName().startsWith("docker")) {
          continue;
        } else {
          return netint.interfaceAddresses[0].address.toString()
        }
      }
      return null
    } catch (e: Throwable) {
      return null
    }
  }


  @JvmStatic
  fun main(arguments: Array<String>) {
    val identifier = generateHostId()
    println(identifier)
  }
}
