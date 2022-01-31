package com.kotlin.gierreprojectwebservice.dto

import kotlinx.serialization.Serializable
import org.springframework.boot.configurationprocessor.json.JSONObject


// ************** POD DTOS *****************
@Serializable
data class KnackResponsePodDto(
    val total_pages: Int,
    val current_page: Int,
    val total_records: Int,
    var records: List<KnackRecordPodDto>
) {}



@Serializable
data class KnackRecordPodDto(
    val id: String,
    var field_25_raw: KnackRawField
) {}




// ************** PDR DTOS *****************
@Serializable
data class KnackResponsePdrDto(
    val total_pages: Int,
    val current_page: Int,
    val total_records: Int,
    var records: List<KnackRecordPdrDto>
) {}



@Serializable
data class KnackRecordPdrDto(
    val id: String,
    var field_26_raw: KnackRawField
) {}


// ******************* MOROSITA DTO **********************
@Serializable
data class KnackResponseMorositaDto(
    val total_pages: Int,
    val current_page: Int,
    val total_records: Int,
    var records: List<KnackRecordMorositaDto>
) {}



@Serializable
data class KnackRecordMorositaDto(
    val id: String,
    var field_15_raw: KnackRawField
) {}




// ******************* MOROSITA DTO **********************
@Serializable
data class KnackResponseControlloGasDto(
    val total_pages: Int,
    val current_page: Int,
    val total_records: Int,
    var records: List<KnackRecordControlloGasDto>
) {}



@Serializable
data class KnackRecordControlloGasDto(
    val id: String,
    var field_11_raw: String, // esito
    val field_12: String, // codice remi
    val field_9: String, // img
    val field_2: String // citta
) {}


// ********************* USATO DA POD, PDR & MOROSITA *********************+
@Serializable
data class KnackRawField(
    val id: String,
    val application_id: String,
    val s3: Boolean,
    val type: String,
    val filename: String,
    val url: String,
    val thumb_url: String,
    val size: Int,
    val field_key: String
) {}