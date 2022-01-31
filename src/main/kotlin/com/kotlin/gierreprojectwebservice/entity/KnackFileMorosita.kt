package com.kotlin.gierreprojectwebservice.entity

import java.time.LocalDateTime
import java.time.ZoneId
import javax.persistence.*

@Entity
@Table(name = "KNACK_FILE_MOROSITA")
class KnackFileMorosita(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "nome", nullable = false, unique = false)
    val nome: String,

    @Lob
    @Column(length=100000)
    var file: ByteArray? = null,

    @Column(name = "createdAt", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(ZoneId.of("Europe/Rome")),
) {}