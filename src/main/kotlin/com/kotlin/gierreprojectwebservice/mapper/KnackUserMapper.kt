package com.kotlin.gierreprojectwebservice.mapper

import com.kotlin.gierreprojectwebservice.dto.KnackUserDto
import com.kotlin.gierreprojectwebservice.entity.KnackUser
import org.springframework.stereotype.Component

@Component
class KnackUserMapper: Mapper<KnackUser, KnackUserDto> {
    override fun toDto(entity: KnackUser) = KnackUserDto(id = entity.id, username = entity.username, password = entity.password, role = entity.role)
    override fun toEntity(dto: KnackUserDto) = KnackUser(id = dto.id, username = dto.username, password = dto.password, role = dto.role)
}