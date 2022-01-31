package com.kotlin.gierreprojectwebservice.entity

import java.time.LocalDateTime
import java.time.ZoneId
import javax.persistence.*


@Entity
@Table(name = "KNACK_RICERCA")
class KnackRicerca(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(name = "tipo", nullable = false)
    val tipo: String,

    @Column(name = "operatore", nullable = false)
    val operatore: String,

    @Column(name = "valore", nullable = false)
    val valore: String,

    @Column(name = "esito", nullable = false)
    val esito: String,

    @Column(name = "createdAt", nullable = false)
    val created: LocalDateTime = LocalDateTime.now(ZoneId.of("Europe/Rome"))
) {}