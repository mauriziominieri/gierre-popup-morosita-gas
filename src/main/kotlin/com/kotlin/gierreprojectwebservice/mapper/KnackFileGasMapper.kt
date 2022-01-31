package com.kotlin.gierreprojectwebservice.mapper

import com.kotlin.gierreprojectwebservice.dto.KnackFileGasDto
import com.kotlin.gierreprojectwebservice.entity.KnackFileGas
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class KnackFileGasMapper(): Mapper<KnackFileGas, KnackFileGasDto> {
    override fun toDto(entity: KnackFileGas) =
        KnackFileGasDto(
            id = entity.id,
            name = entity.nome,
            createdAt = entity.createdAt
        )

    override fun toEntity(dto: KnackFileGasDto) =
        KnackFileGas(
            id = dto.id,
            nome = dto.name,
            file = null,
            createdAt = dto.createdAt
        )
}