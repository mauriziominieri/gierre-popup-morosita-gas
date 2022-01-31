package com.kotlin.gierreprojectwebservice.dto

import java.time.LocalDateTime

data class ErrorDetailsDto(val date: LocalDateTime, val message: String, val description: String ) {}