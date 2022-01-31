package com.kotlin.gierreprojectwebservice.repository

import com.kotlin.gierreprojectwebservice.entity.KnackRicerca
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface KnackRicercaRepository: JpaRepository<KnackRicerca, Long> {}