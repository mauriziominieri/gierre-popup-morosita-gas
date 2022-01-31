package com.kotlin.gierreprojectwebservice.controller

import com.kotlin.gierreprojectwebservice.dto.KnackFileDto
import com.kotlin.gierreprojectwebservice.dto.KnackUserDto
import com.kotlin.gierreprojectwebservice.dto.ServerResponseDto
import com.kotlin.gierreprojectwebservice.service.KnackService
import com.kotlin.gierreprojectwebservice.service.KnackUserService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.logging.Logger


@RestController
@RequestMapping(value = ["/v1/api/knack/user"])
class KnackUserRestController(
    private val userKnackService: KnackUserService
) {

    companion object {
        @JvmStatic private val logger = Logger.getLogger(KnackUserRestController::class.java.name)
    }

    @ApiOperation(value = "Richiesta di login")
    @PostMapping(value = ["/login"], produces = ["application/json"])
    fun login(
        @ApiParam(value = "username") @RequestParam("username") username: String,
        @ApiParam(value = "password") @RequestParam("password") password: String,
    ): ResponseEntity<ServerResponseDto<KnackUserDto>> {
        logger.info { "API: POST [/login] called" }
        return ResponseEntity.ok(this.userKnackService.login(username, password))
    }

    @ApiOperation(value = "Richiesta per estrarre la lista degli utenti")
    @PostMapping(value = ["/get/all"], produces = ["application/json"])
    fun getKnackUser(@ApiParam(value = "email") @RequestParam("email") email: String): ResponseEntity<ServerResponseDto<List<KnackUserDto>>> {
        logger.info { "API: POST [/get/all] called" }
        return ResponseEntity.ok(this.userKnackService.getUsers(email))
    }

    @ApiOperation(value = "Richiesta per estrarre la lista ruoli utenti")
    @PostMapping(value = ["/allRuoli"], produces = ["application/json"])
    fun getKnackRoles(@ApiParam(value = "email") @RequestParam("email") email: String): ResponseEntity<ServerResponseDto<List<Int>>> {
        logger.info { "API: POST [/allRuoli] called" }
        return ResponseEntity.ok(this.userKnackService.getRoles(email))
    }

    @ApiOperation(value = "Richiesta per creare un nuovo utente")
    @PostMapping(value = ["/create"], produces = ["application/json"])
    fun createKnackUser(
        @ApiParam(value = "email") @RequestParam("email") email: String,
        @ApiParam(value = "knackUser") @RequestBody user: KnackUserDto
    ): ResponseEntity<ServerResponseDto<KnackUserDto>> {
        logger.info { "API: POST [/create] called" }
        return ResponseEntity.ok(this.userKnackService.createUser(email, user))
    }


    @ApiOperation(value = "Richiesta per cancellare un utente")
    @DeleteMapping(value = ["/delete"], produces = ["application/json"])
    fun deleteKnackUser(
        @ApiParam(value = "emailAdmin") @RequestParam("emailAdmin") emailAdmin: String,
        @ApiParam(value = "emailUserToDelete") @RequestParam("emailUser") emailUser: String
    ): ResponseEntity<ServerResponseDto<KnackUserDto>> {
        logger.info { "API: POST [/delete] called" }
        return ResponseEntity.ok(this.userKnackService.deleteUser(emailAdmin, emailUser))
    }

    /*@ApiOperation(value = "Richiesta per registrare un utente")
    @PostMapping(value = ["/register"], produces = ["application/json"])
    fun registerKnackUser(
        @ApiParam(value = "user") @RequestBody user: KnackUserDto
    ): ResponseEntity<ServerResponseDto<KnackUserDto>> {
        logger.info { "API: POST [/register] called" }
        return ResponseEntity.ok(this.userKnackService.register(user))
    }*/
}