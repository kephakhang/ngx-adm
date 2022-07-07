package com.youngplussoft.admin.server.service

import com.youngplussoft.admin.server.jpa.own.entity.Terminal
import com.youngplussoft.admin.server.jpa.own.repository.CounterRepository
import com.youngplussoft.admin.server.jpa.own.repository.TerminalRepository

class TerminalService(val db: TerminalRepository, val counterDb: CounterRepository): BaseService<Terminal>(db) {

    fun getListByTeId(tenantId: String?): List<Terminal> {
        return db.findAllByTeId(tenantId)
    }
}
