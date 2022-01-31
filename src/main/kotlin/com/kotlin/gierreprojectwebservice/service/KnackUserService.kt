package com.kotlin.gierreprojectwebservice.service

import com.kotlin.gierreprojectwebservice.dto.KnackUserDto
import com.kotlin.gierreprojectwebservice.dto.ServerResponseDto

interface KnackUserService {
    fun login(username: String, password: String): ServerResponseDto<KnackUserDto>
    fun getUsers(email: String): ServerResponseDto<List<KnackUserDto>>
    fun getRoles(email: String): ServerResponseDto<List<Int>>
    fun createUser(emailAdmin: String, user: KnackUserDto): ServerResponseDto<KnackUserDto>
    fun deleteUser(emailAdmin: String, userToDelete: String): ServerResponseDto<KnackUserDto>
    fun register(user: KnackUserDto): ServerResponseDto<KnackUserDto>
}