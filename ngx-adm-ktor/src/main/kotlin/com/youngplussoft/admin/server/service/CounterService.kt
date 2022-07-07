package com.youngplussoft.admin.server.service

import com.youngplussoft.admin.server.jpa.own.entity.Counter
import com.youngplussoft.admin.server.jpa.own.repository.CounterRepository

class CounterService(val db: CounterRepository): BaseService<Counter>(db) {

    @Throws(Exception::class)
    fun getListByTeIdAndTrId(tenantId: String?, terminalId: String?): List<Counter> {
        return db.findAllByTeIdAndTrId(tenantId, terminalId)
    }

}
