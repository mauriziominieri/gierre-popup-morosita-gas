package com.kotlin.gierreprojectwebservice.deprecated

import com.kotlin.gierreprojectwebservice.dto.KnackRequestControlloGasDto
import com.kotlin.gierreprojectwebservice.dto.KnackRequestDto
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class KnackHelperFactory() {

    sealed class KnackCall(){
        class KnackPod(): KnackCall()
        class KnackPdr(): KnackCall()
        class KnackMorosita(): KnackCall()
        class KnackControlloGas(): KnackCall()
    }


    fun getRequestDtoForPopupPod(data: String): KnackRequestDto {
        return KnackRequestDto(
            ignore_operators = false,
            operator = "contains",
            operator_default = "contains",
            multi_type = "one",
            multi_input = "chosen",
            multi_match = "or",
            field = "keyword_search",
            name = "Inserisci Pod%26nbsp;<span class=\"kn-required\">*</span>",
            required = true,
            value = data
        )
    }


    fun getRequestDtoForPopupPdr(data: String): KnackRequestDto {
        return KnackRequestDto(
            ignore_operators = false,
            operator = "contains",
            operator_default = "contains",
            multi_type = "one",
            multi_input = "chosen",
            multi_match = "or",
            field = "keyword_search",
            name = "Inserisci Pdr%26nbsp;<span class=\"kn-required\">*</span>",
            required = true,
            value = data
        )
    }


    fun getRequestDtoForControlloGas(citta: List<String>): KnackRequestControlloGasDto {
        return KnackRequestControlloGasDto(field = "field_2", operator = "is all", value = citta)
    }


    fun createKnackHttpRequest(url: String, data: List<String>, type: KnackCall): HttpClientHelper {

        val parameters: MutableList<Pair<String, String>> = mutableListOf()

        val knackDto: Any = when(type) {
            is KnackCall.KnackPod -> {
                this.getRequestDtoForPopupPod(data[0])
            }
            is KnackCall.KnackPdr -> {
                this.getRequestDtoForPopupPdr(data[0])
            }
            is KnackCall.KnackMorosita -> {
                this.getRequestDtoForPopupPdr(data[0])
            }
            is KnackCall.KnackControlloGas -> {
                this.getRequestDtoForControlloGas(data)
            }
        }

        val headers: MutableList<Pair<String, String>> = mutableListOf()
        //headers.add(Pair("Host", "eu-central-1-renderer-read.knack.com"))
        headers.add(Pair("X-Knack-REST-API-Key", "renderer"))

        if(type is KnackCall.KnackControlloGas) {
            val filters = Json.encodeToString(listOf(knackDto as KnackRequestControlloGasDto))
            parameters.add(Pair("filters", filters))
            headers.add(Pair("X-Knack-Application-Id", "6196ba5a9cb51d00227a30ab"))
        }
        else {
            parameters.add(Pair("filters", Json.encodeToString(listOf(knackDto as KnackRequestDto))))
            headers.add(Pair("X-Knack-Application-Id", "619e169ead63f0001f457df6"))
        }


        val request = HttpClientHelper
            .factory()
            .addHeaders(headers)
            .addParams(parameters)
            .createRequest(url, "GET")

        return request
    }

}

/*
* {\"ignore_operators\":false,\"operator\":\"contains\",\"operator_default\":\"contains\",\"multi_type\":\"one\",\"multi_input\":\"chosen\",
* \"multi_match\":\"or\",\"field\":\"keyword_search\",\"name\":\"Inserisci Pod%26nbsp;<span class=\\\"kn-required\\\">*</span>\",\"required\":true,\"value\":\"IT001E80725139\"}]"))

        val response = HttpClientHelper*/