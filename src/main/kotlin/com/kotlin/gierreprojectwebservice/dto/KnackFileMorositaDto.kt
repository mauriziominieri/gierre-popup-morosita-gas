package com.kotlin.gierreprojectwebservice.dto

import java.io.Serializable
import java.time.LocalDateTime
import java.time.ZoneId

class KnackFileMorositaDto(
    val id: Long?,
    val name: String,
    var createdAt: LocalDateTime = LocalDateTime.now(ZoneId.of("Europe/Rome")),
): Serializable {}