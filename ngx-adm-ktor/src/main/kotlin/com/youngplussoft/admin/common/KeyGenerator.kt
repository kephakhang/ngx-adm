package com.youngplussoft.admin.common

import com.youngplussoft.admin.extensions.sha256
import com.youngplussoft.admin.server.auth.BcryptHasher
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.nio.ByteBuffer
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*

class KeyGenerator {
  companion object {

    fun generateMobileAuth(): String {
      val r = Random()
      return String.format("%07d", r.nextInt(10000000))
    }

    fun generateOrderNo(): String {
      val now = Date()
      val transFormat = SimpleDateFormat("yyyyMMddHHmmssSSS")
      val to = transFormat.format(now)
      val random = Random(now.time)
      return to + String.format("%04d", random.nextInt() % 10000)
    }

    fun removeEnd(str: String, remove: String): String {
      if (!str.isEmpty() && !remove.isEmpty()) {
        if (str.endsWith(remove))
          return str.substring(0, str.length - remove.length)
        else
          return str;
      } else {
        return str;
      }
    }

    /*
    *  randomUUID 생성
    * @return  id 스트링
    */
    fun uuid(): String {
      return UUID.randomUUID().toString()
    }

    /*
   *  randomUUID 를  Base64  코드 형식으로 자동 생성
   * @return  uuid 스트링
   */
    fun generateKey(): String {
      val uuid = UUID.randomUUID()
      val uuidArray: ByteArray = KeyGenerator.toByteArray(uuid)
      val encodedArray: ByteArray = Base64.getEncoder().encode(uuidArray)
      var returnValue = String(encodedArray)
      returnValue = this.removeEnd(returnValue, "\r\n")
      return returnValue
    }

    /*
   *  입력된 아규먼트 문장을  sha256  암호로 변경
   * @param 	str  입력 문장
   * @return  sha256 암호로 변환된 스트링
   */
    fun sha256(str: String): String? {

      return try {
        str.sha256()
      } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
        null
      }
    }

    /*
 *  입력된 아규먼트 문장을  sha256  암호로 변경(SEED 포함)
 * @param 	str  입력 문장
 * @return  sha256 암호로 변환된 스트링
 */
    fun password(str: String): String {
      return BcryptHasher.hashPassword(str)
    }

    /*
 *  randomUUID 를  sha256   암호 코드 형식으로 자동 생성
 * @return   sha256  암호 스트링
 */
    fun generateSha256(): String? {
      return sha256(UUID.randomUUID().toString())
    }

    /*
 *  BASE64  코드를 원래의  random UUID  스트링으로 변환
 * @return   random UUID  스트링
 */
    fun convertUUID(key: String): String {
      val encodedArray: ByteArray = Base64.getEncoder().encode(key.toByteArray())
      var returnValue = String(encodedArray)
      returnValue = this.removeEnd(returnValue, "\r\n")
      return returnValue
    }

    /*
 *  BASE64  코드를 원래의  random UUID 객체로 변환
 * @return   random UUID  객체
 */
    fun convertKey(key: String?): UUID? {
      var returnValue: UUID? = null
      if (key!!.isNotBlank()) {
        // Convert base64 string to a byte array
        val decodedArray: ByteArray = Base64.getDecoder().decode(key)
        returnValue = KeyGenerator.fromByteArray(decodedArray)
      }
      return returnValue
    }

    /*
 *  UUID 객체를  byte  어레이로 변환
 * @param 	uuid UUID  객체
 * @return   변환된  byte  어레이
 */
    private fun toByteArray(uuid: UUID): ByteArray {
      val byteArray = ByteArray(java.lang.Long.SIZE / java.lang.Byte.SIZE * 2)
      val buffer = ByteBuffer.wrap(byteArray)
      val longBuffer = buffer.asLongBuffer()
      longBuffer.put(longArrayOf(uuid.mostSignificantBits, uuid.leastSignificantBits))
      return byteArray
    }

    /*
 *  byte  어레이를  UUID  객체로 변환
 * @param 	 bytes  변환할  byte  어레이
 * @return   변환된   UUID 객체
 */
    private fun fromByteArray(bytes: ByteArray): UUID {
      val buffer = ByteBuffer.wrap(bytes)
      val longBuffer = buffer.asLongBuffer()
      return UUID(longBuffer[0], longBuffer[1])
    }

    @JvmStatic
    fun main(argv: Array<String>) {
      System.out.println(KeyGenerator.generateKey())

      val body = Json {
        "requestId" to "12345678915"
        "tenantId" to "dev:oem:us"
        "moldId" to "test"
        "data" to Json {
          "cycleTime" to Json {
            "hourly" to arrayOf(30)
            "weyekly" to arrayOf(33)
            "daily" to arrayOf(37)
          }
          "shotCount" to Json {
            "hourly" to arrayOf(300)
            "weyekly" to arrayOf(330)
            "daily" to arrayOf(370)
          }
          "temperature" to Json {
            "hourly" to arrayOf(300, 400, 500)
            "weekly" to arrayOf(300, 400, 500)
            "daily" to arrayOf(300, 400, 500)
          }
        }
      }

      System.out.println(body.encodeToString(body.serializersModule))
    }
  }
}
