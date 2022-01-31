package com.kotlin.gierreprojectwebservice.dto

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

data class KnackFileDto(
   val id: Long?,
   val name: String,
   val status: String,
   val createdAt: LocalDateTime,
) {}