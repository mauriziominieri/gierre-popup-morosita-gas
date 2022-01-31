package com.kotlin.gierreprojectwebservice.repository

import com.kotlin.gierreprojectwebservice.entity.KnackFileGas
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface KnackFileGasRepository: JpaRepository<KnackFileGas, Long> {
}