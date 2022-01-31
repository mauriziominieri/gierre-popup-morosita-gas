package com.kotlin.gierreprojectwebservice.controller

import com.kotlin.gierreprojectwebservice.dto.ErrorDetailsDto
import com.kotlin.gierreprojectwebservice.dto.ServerResponseDto
import com.kotlin.gierreprojectwebservice.exception.*
import org.hibernate.exception.GenericJDBCException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.orm.jpa.JpaSystemException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime
import java.util.logging.Logger

@ControllerAdvice
class ExceptionControllerAdvice {

    companion object {
        @JvmStatic private val logger = Logger.getLogger(ExceptionControllerAdvice::class.java.name)
    }


    @ExceptionHandler(EntityNotFoundIdException::class)
    fun exceptionEntityNotFoundIdHandler(e: EntityNotFoundIdException): ResponseEntity<ServerResponseDto<ErrorDetailsDto>> {

        logger.severe(e.printStackTrace().toString())

        val error = ErrorDetailsDto(
            LocalDateTime.now(),
            "Operation Failed: EntityNotFoundIdException",
            e.localizedMessage
        )

        val responseError: ServerResponseDto<ErrorDetailsDto> = ServerResponseDto.badRequest(error)
        return ResponseEntity.badRequest().body(responseError);
    }



    @ExceptionHandler(ParameterNotValidException::class)
    fun exceptionParameterNotValidHandler(e: ParameterNotValidException): ResponseEntity<ServerResponseDto<ErrorDetailsDto>> {

        logger.severe(e.printStackTrace().toString())

        val error = ErrorDetailsDto(
            LocalDateTime.now(),
            "Operation Failed: ParameterNotValidException",
            e.localizedMessage
        )

        val responseError: ServerResponseDto<ErrorDetailsDto> = ServerResponseDto.badRequest(error)
        return ResponseEntity.badRequest().body(responseError);
    }


    @ExceptionHandler(FileExcelException::class)
    fun exceptionHandler(e: FileExcelException): ResponseEntity<ServerResponseDto<ErrorDetailsDto>> {

        logger.severe(e.printStackTrace().toString())

        val error = ErrorDetailsDto(
            LocalDateTime.now(),
            "FileExcelException",
            e.localizedMessage
        )

        val responseError: ServerResponseDto<ErrorDetailsDto> = ServerResponseDto.badRequest(error)
        return ResponseEntity.badRequest().body(responseError);
    }


    @ExceptionHandler(KnackUserUserPasswordNotValidException::class)
    fun exceptionHandler(e: KnackUserUserPasswordNotValidException): ResponseEntity<ServerResponseDto<ErrorDetailsDto>> {

        logger.severe(e.printStackTrace().toString())

        val error = ErrorDetailsDto(
            LocalDateTime.now(),
            "UserException",
            e.localizedMessage
        )

        val responseError: ServerResponseDto<ErrorDetailsDto> = ServerResponseDto(HttpStatus.UNAUTHORIZED, ServerResponseDto.FAILED, error)

        return ResponseEntity(responseError, HttpStatus.UNAUTHORIZED)
    }



    @ExceptionHandler(KnackUserUserNotFoundException::class)
    fun exceptionUnauthorizedHandler(e: KnackUserUserNotFoundException): ResponseEntity<ServerResponseDto<ErrorDetailsDto>> {

        logger.severe(e.printStackTrace().toString())

        val error = ErrorDetailsDto(
            LocalDateTime.now(),
            "UserException",
            e.localizedMessage
        )

        val responseError: ServerResponseDto<ErrorDetailsDto> = ServerResponseDto(HttpStatus.UNAUTHORIZED, ServerResponseDto.FAILED, error)

        return ResponseEntity(responseError, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(KnackUserAlreadyPresentException::class)
    fun exceptionUserAlreadyPresentHandler(e: KnackUserAlreadyPresentException): ResponseEntity<ServerResponseDto<ErrorDetailsDto>> {

        logger.severe(e.printStackTrace().toString())

        val error = ErrorDetailsDto(
            LocalDateTime.now(),
            "UserException",
            e.message
        )

        val responseError: ServerResponseDto<ErrorDetailsDto> = ServerResponseDto.badRequest(error)
        return ResponseEntity.badRequest().body(responseError);
    }

    @ExceptionHandler(JpaSystemException::class)
    fun jpaSystemExceptionHandler(e: JpaSystemException): ResponseEntity<ServerResponseDto<ErrorDetailsDto>> {

        logger.severe(e.printStackTrace().toString())

        val error = ErrorDetailsDto(
            LocalDateTime.now(),
            "JpaSystemException",
            e.mostSpecificCause.toString()
        )

        val responseError: ServerResponseDto<ErrorDetailsDto> = ServerResponseDto.badRequest(error)
        return ResponseEntity.badRequest().body(responseError);
    }

    @ExceptionHandler(Exception::class)
    fun exceptionHandler(e: Exception): ResponseEntity<ServerResponseDto<ErrorDetailsDto>> {

        logger.severe(e.printStackTrace().toString())

        val error = ErrorDetailsDto(
            LocalDateTime.now(),
            "Generic Error",
            "Error encountered during the execution of the program."
        )
        val responseError: ServerResponseDto<ErrorDetailsDto> = ServerResponseDto.error(error)
        return ResponseEntity.internalServerError().body(responseError);
    }

}