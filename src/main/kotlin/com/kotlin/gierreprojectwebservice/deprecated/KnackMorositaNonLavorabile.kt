package com.kotlin.gierreprojectwebservice.deprecated

import com.kotlin.gierreprojectwebservice.entity.KnackFileMorosita
import java.time.LocalDateTime
import java.time.ZoneId
import javax.persistence.*

/*
@Entity
@Table(name = "KNACK_MOROSITA_NON_LAVORABILI")
class KnackMorositaNonLavorabile(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(name = "type", nullable = false)
    val type: String,

    @Column(name = "data", unique = true, nullable = false)
    val data: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fileId")
    val file: KnackFileMorosita,

    @Column(name = "createdAt", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(ZoneId.of("Europe/Rome"))
) {}*/