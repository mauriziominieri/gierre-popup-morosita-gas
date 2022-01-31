package com.kotlin.gierreprojectwebservice.helper

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
class KnackExcelPopupRow(
    @SerializedName("POD")
    val pod: String?,

    @SerializedName("PDR")
    val pdr: String?,

    @SerializedName("Data creaz.")
    val createdAt: String
) {}

@Serializable
class KnackExcelMorositaRow(
    @SerializedName("POD/PDR")
    val podpdr: String
) {}

@Serializable
class KnackExcelGasRow(
    @SerializedName("DISTRIBUTORE")
    val distributore: String,

    @SerializedName("Denominazione Comune")
    val denominazioneComune: String,

    @SerializedName("Denominazione City Gate")
    val denominazioneCityGate: String,

    @SerializedName("REMI")
    val remi: String,

    @SerializedName("LAVORABILITA PLT")
    val lavorabilitaPlt: String
) {}

class KnackEsitoPopupExcelRow() {
    var pod: String = ""
    var pdr: String = ""
    var data_creazione: String = ""
    var check_popup_pdr: String = ""
    var check_popup_pod: String = ""
}

class KnackEsitoMorositaExcelRow() {
    var podpdr: String = ""
    var esito: String = ""
}

class KnackEsitoGasExcelRow() {
    val denominazioneComune: String = ""
    val denominazioneCityGate: String = ""
    var esito: String = ""
}