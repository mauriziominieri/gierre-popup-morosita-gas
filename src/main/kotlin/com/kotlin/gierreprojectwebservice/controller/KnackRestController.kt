package com.kotlin.gierreprojectwebservice.controller

import com.kotlin.gierreprojectwebservice.dto.*
import com.kotlin.gierreprojectwebservice.service.KnackService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.logging.Logger

@RestController
@RequestMapping("/v1/api/knack")
class KnackRestController(
    private val knackService: KnackService
) {
    companion object {
        @JvmStatic private val logger = Logger.getLogger(KnackRestController::class.java.name)
    }


    @ApiOperation(value = "Richiesta per verificare il pod sui servizi Knack")
    @PostMapping(value = ["/check/pod/{pod}"], produces = ["application/json"])
    fun checkPod(@ApiParam(value = "pod") @PathVariable pod: String, @RequestParam("email") email: String): ResponseEntity<ServerResponseDto<String>> {
        logger.info { "API: GET [/check/pod/$pod] called" }
        return ResponseEntity.ok(this.knackService.checkPod(pod.uppercase(), email))
    }


    @ApiOperation(value = "Richiesta per verificare il pdr sui servizi Knack")
    @PostMapping(value = ["/check/pdr/{pdr}"], produces = ["application/json"])
    fun checkPdr(@ApiParam(value = "pdr") @PathVariable pdr: String, @RequestParam("email") email: String): ResponseEntity<ServerResponseDto<String>> {
        logger.info { "API: GET [/check/pdr/$pdr] called" }
        return ResponseEntity.ok(this.knackService.checkPdr(pdr.uppercase(), email))
    }


    @ApiOperation(value = "Richiesta per verificare la morosità sui servizi Knack")
    @PostMapping(value = ["/check/morosita/{type}/{data}"], produces = ["application/json"])
    fun checkMorosita(@ApiParam(value = "type") @PathVariable type: String, @ApiParam(value = "data") @PathVariable data: String, @RequestParam("email") email: String): ResponseEntity<ServerResponseDto<String>> {
        logger.info { "API: GET [/check/morosita/$type/$data] called" }
        return ResponseEntity.ok(this.knackService.checkMorosita(data.uppercase(), type.uppercase(), email))
    }


    @ApiOperation(value = "Richiesta per effettuare il controllo gas sui servizi Knack")
    @PostMapping(value = ["/check/controllo/gas/{comune}"], produces = ["application/json"])
    fun checkGas(@ApiParam(value = "comune") @PathVariable comune: String, @RequestParam("email") email: String): ResponseEntity<ServerResponseDto<List<KnackControlloGasResponseDto>>> {
        logger.info { "API: GET [/check/controllo/gas/${comune.toString()}] called" }
        return ResponseEntity.ok(this.knackService.checkGas(comune.lowercase(), email))
    }

    /*@ApiOperation(value = "Richiesta per effettuare il controllo gas sui servizi Knack")
    @GetMapping(value = ["/check/controllo/gas/"], produces = ["application/json"])
    fun loadGas(@ApiParam(value = "lista di citta") @RequestParam citta: List<String>): ResponseEntity<ServerResponseDto<List<KnackControlloGasDto>>> {
        logger.info { "API: GET [/check/controllo/gas/${citta.toString()}] called" }
        return ResponseEntity.ok(this.knackService.checkGas(citta))
    }*/

    @ApiOperation(value = "Richiesta per estrapolare tutte le ricerche fatte")
    @GetMapping(value = ["/get/ricerche"], produces = ["application/json"])
    fun getRicerche(): ResponseEntity<ServerResponseDto<List<KnackRicercaDto>>> {
        logger.info { "API: POST [/load] called" }
        return ResponseEntity.ok(this.knackService.get())
    }
}