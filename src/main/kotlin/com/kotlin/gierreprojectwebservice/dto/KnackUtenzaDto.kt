package com.kotlin.gierreprojectwebservice.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import javax.persistence.Column

data class KnackUtenzaDto(
    val id: Long?,
    var data: String = "",
    var type: String = "",
    var operatore: String = "",
    var dataInserimento: String = "",
    val createdAt: LocalDateTime,
    var checkPopup: String = "",
    var morosita: String = "",
) {}