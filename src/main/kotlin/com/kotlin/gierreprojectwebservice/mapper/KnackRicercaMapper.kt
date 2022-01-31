package com.kotlin.gierreprojectwebservice.mapper

import com.kotlin.gierreprojectwebservice.dto.KnackRicercaDto
import com.kotlin.gierreprojectwebservice.dto.KnackUtenzaDto
import com.kotlin.gierreprojectwebservice.entity.KnackRicerca
import org.springframework.stereotype.Component

@Component
class KnackRicercaMapper: Mapper<KnackRicerca, KnackRicercaDto> {

    override fun toDto(entity: KnackRicerca): KnackRicercaDto =
        KnackRicercaDto(
            id = entity.id,
            data = entity.created,
            operatore = entity.operatore,
            valore = entity.valore,
            tipo = entity.tipo,
            esito = entity.esito
        )


    override fun toEntity(dto: KnackRicercaDto): KnackRicerca =
        KnackRicerca(
            id = dto.id,
            created = dto.data,
            operatore = dto.operatore,
            valore = dto.valore,
            tipo = dto.tipo,
            esito = dto.esito
        )


    /*fun toRowsExcel(listEntities: List<KnackUtenza>): List<KnackEsitoExcelRow> {
        val map = hashMapOf<Long, KnackEsitoExcelRow>()

        listEntities.forEach {
            val obj = map[it.row] ?: KnackEsitoExcelRow()

            when(it.type){
                "POD" -> {
                    obj.pod = it.data
                    obj.check_popup_pod = it.checkPopup
                    obj.check_morosita_pod = it.morosita
                }
                "PDR" -> {
                    obj.pdr = it.data
                    obj.check_popup_pdr = it.checkPopup
                    obj.check_morosita_pdr = it.morosita
                }
            }

            obj.data_creazione = it.dataInserimento

            map[it.row!!] = obj
        }

        return map.values.toList()
    }*/
}