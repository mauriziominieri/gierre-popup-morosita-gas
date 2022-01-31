package com.kotlin.gierreprojectwebservice.service

import com.kotlin.gierreprojectwebservice.dto.*
import org.springframework.web.multipart.MultipartFile

interface KnackService {
    fun checkPod(pod: String, email: String): ServerResponseDto<String>
    fun checkPdr(pdr: String, email: String): ServerResponseDto<String>
    fun checkMorosita(data: String, type: String, email: String): ServerResponseDto<String>
    fun checkGas(comune: String, email: String): ServerResponseDto<List<KnackControlloGasResponseDto>>
    fun get(): ServerResponseDto<List<KnackRicercaDto>>
}