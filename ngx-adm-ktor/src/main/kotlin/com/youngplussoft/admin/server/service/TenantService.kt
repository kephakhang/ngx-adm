package com.youngplussoft.admin.server.service

import com.youngplussoft.admin.server.jpa.own.entity.Tenant
import com.youngplussoft.admin.server.jpa.own.repository.TenantRepository

class TenantService(val db: TenantRepository): BaseService<Tenant>(db) {

}
