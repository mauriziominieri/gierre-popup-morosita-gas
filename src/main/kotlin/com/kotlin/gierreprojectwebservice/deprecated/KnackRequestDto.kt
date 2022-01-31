package com.kotlin.gierreprojectwebservice.dto

import kotlinx.serialization.Serializable

@Serializable
data class KnackRequestDto(
    val ignore_operators: Boolean,
    val operator: String,
    val operator_default: String,
    val multi_type: String,
    val multi_input: String,
    val multi_match: String,
    val field: String,
    val name: String,
    val required: Boolean,
    val value: String
) {}


@Serializable
data class KnackRequestControlloGasDto(
    var field: String,
    var operator: String,
    var value: List<String>
) {}