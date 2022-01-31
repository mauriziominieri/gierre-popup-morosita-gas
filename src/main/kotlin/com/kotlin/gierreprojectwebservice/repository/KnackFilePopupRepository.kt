package com.kotlin.gierreprojectwebservice.repository

import com.kotlin.gierreprojectwebservice.entity.KnackFileMorosita
import com.kotlin.gierreprojectwebservice.entity.KnackFilePopup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface KnackFilePopupRepository: JpaRepository<KnackFilePopup, Long> {
}