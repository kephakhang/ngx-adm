package com.youngplussoft.admin.server.service

import com.youngplussoft.admin.server.jpa.own.dto.UserDto
import com.youngplussoft.admin.server.jpa.own.dto.UserUpdateDto
import com.youngplussoft.admin.server.jpa.own.entity.Member
import com.youngplussoft.admin.server.jpa.own.repository.MemberRepository

class UserService(val db: MemberRepository): BaseService<Member>(db) {

    fun getListByLevel(level: Int, pageno: Long, pagesize: Long): List<Member> {
        return db.findAllByLevel(level,(pageno - 1) * pagesize, pagesize)
    }

    fun updateUser(userUpdateDto: UserUpdateDto): Member? {
        repository.transaction {
            db.updateUser(userUpdateDto)
        }
        val entity = db.findById(userUpdateDto.id)
        return entity
    }
}
