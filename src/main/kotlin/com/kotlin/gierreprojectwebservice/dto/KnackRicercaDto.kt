package com.kotlin.gierreprojectwebservice.dto

import java.time.LocalDateTime

class KnackRicercaDto(
    var id: Long? = null,
    val data: LocalDateTime,
    val operatore: String,
    val tipo: String,
    val valore: String,
    val esito: String
) {}