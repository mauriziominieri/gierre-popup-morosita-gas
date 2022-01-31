package com.kotlin.gierreprojectwebservice.mapper

import com.kotlin.gierreprojectwebservice.dto.KnackFilePopupDto
import com.kotlin.gierreprojectwebservice.entity.KnackFilePopup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class KnackFilePopupMapper(): Mapper<KnackFilePopup, KnackFilePopupDto> {
    override fun toDto(entity: KnackFilePopup) =
        KnackFilePopupDto(
            id = entity.id,
            name = entity.nome,
            createdAt = entity.createdAt
        )

    override fun toEntity(dto: KnackFilePopupDto) =
        KnackFilePopup(
            id = dto.id,
            nome = dto.name,
            file = null,
            createdAt = dto.createdAt
        )
}