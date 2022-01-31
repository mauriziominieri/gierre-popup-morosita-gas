package com.kotlin.gierreprojectwebservice.dto

import org.springframework.http.HttpStatus

class ServerResponseDto<T>(
    val status: HttpStatus,
    val message: String,
    val data: T) {

    companion object {
        @JvmStatic var SUCCESS = "La richiesta è stata completata."
        @JvmStatic var FAILED = "Ops, qualcosa è andato storto."

        @JvmStatic
        fun <T> ok(obj: T, status: HttpStatus? = null): ServerResponseDto<T> {
            return ServerResponseDto( status ?: HttpStatus.OK, ServerResponseDto.SUCCESS, obj)
        }

        @JvmStatic
        fun <T> error(obj: T): ServerResponseDto<T> {
            return ServerResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, ServerResponseDto.FAILED, obj)
        }

        @JvmStatic
        fun <T> badRequest(obj: T): ServerResponseDto<T> {
            return ServerResponseDto(HttpStatus.BAD_REQUEST, ServerResponseDto.FAILED, obj)
        }
    }
}