package com.youngplussoft.admin.server.jpa.own.enum

enum class TenantType(val value: String, val no: Int) {
  YoungPlusSoft("own", 0), //YoungPlusSoft tenant
  OEM("oem", 1), // OEM tenant
  MOLD_MAKER("mold-maker", 2), // Mold Maker tenant
  SUPPLY("supply", 3) // Supply tenant
}
