package com.kotlin.gierreprojectwebservice.repository

import com.kotlin.gierreprojectwebservice.entity.KnackUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface KnackUserRepository: JpaRepository<KnackUser, Long> {
    fun findDistinctByUsername(username: String): Optional<KnackUser>

    @Query("SELECT DISTINCT(u.role) FROM KnackUser u order by u.role desc")
    fun findAllRoles(): List<Int>
}