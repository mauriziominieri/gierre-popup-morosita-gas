package com.kotlin.gierreprojectwebservice.deprecated

import com.kotlin.gierreprojectwebservice.entity.KnackFilePopup
import java.time.LocalDateTime
import java.time.ZoneId
import javax.persistence.*

/*
@Entity
@Table(name = "KNACK_POPUP_NON_LAVORABILI")
class KnackPopupNonLavorabile(

    @Id
    val data: String,

    @Column(name = "type", nullable = false)
    var type: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fileId")
    val file: KnackFilePopup,

    @Column(name = "createdAt", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(ZoneId.of("Europe/Rome"))
) {}*/