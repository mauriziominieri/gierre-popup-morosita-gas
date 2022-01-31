package com.kotlin.gierreprojectwebservice.mapper

import com.kotlin.gierreprojectwebservice.dto.KnackFileMorositaDto
import com.kotlin.gierreprojectwebservice.entity.KnackFileMorosita
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class KnackFileMorositaMapper(): Mapper<KnackFileMorosita, KnackFileMorositaDto> {
    override fun toDto(entity: KnackFileMorosita) =
        KnackFileMorositaDto(
            id = entity.id,
            name = entity.nome,
            createdAt = entity.createdAt
        )

    override fun toEntity(dto: KnackFileMorositaDto) =
        KnackFileMorosita(
            id = dto.id,
            nome = dto.name,
            file = null,
            createdAt = dto.createdAt
        )
}