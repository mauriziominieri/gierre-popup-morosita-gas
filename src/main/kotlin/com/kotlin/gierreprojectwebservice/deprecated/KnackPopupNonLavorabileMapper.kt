package com.kotlin.gierreprojectwebservice.deprecated

import com.kotlin.gierreprojectwebservice.entity.KnackFilePopup
import com.kotlin.gierreprojectwebservice.mapper.Mapper
import org.springframework.stereotype.Component


/*
@Component
class KnackPopupNonLavorabileMapper: Mapper<KnackPopupNonLavorabile, KnackPopupNonLavorabileDto> {

    override fun toDto(entity: KnackPopupNonLavorabile) =
        KnackPopupNonLavorabileDto(
            //id = entity.id,
            type = entity.type,
            data = entity.data,
            fileId = entity.file.id,
            createdAt = entity.createdAt
        )

    override fun toEntity(dto: KnackPopupNonLavorabileDto) =
        KnackPopupNonLavorabile(
            //id = dto.id,
            type = dto.type,
            data = dto.data,
            file = KnackFilePopup(id = dto.fileId, nome = ""),
            createdAt = dto.createdAt
        )

}*/