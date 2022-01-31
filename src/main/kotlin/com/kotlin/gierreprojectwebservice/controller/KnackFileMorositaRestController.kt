package com.kotlin.gierreprojectwebservice.controller

import com.kotlin.gierreprojectwebservice.dto.KnackFileMorositaDto
import com.kotlin.gierreprojectwebservice.dto.KnackFilePopupDto
import com.kotlin.gierreprojectwebservice.dto.ServerResponseDto
import com.kotlin.gierreprojectwebservice.service.KnackFileMorositaService
import com.kotlin.gierreprojectwebservice.service.KnackFilePopupService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.util.logging.Logger


@RestController
@RequestMapping("/v1/api/knack/file/morosita")
class KnackFileMorositaRestController {
    @Autowired
    private lateinit var knackMorositaService: KnackFileMorositaService

    companion object {
        @JvmStatic private val logger = Logger.getLogger(KnackFilePopupRestController::class.java.name)
    }

    @ApiOperation(value = "Richiesta per prendere i dati di un file precedentemente elaborato")
    @PostMapping(value = ["/add"], produces = ["application/json"])
    fun add(@RequestParam("file") file: MultipartFile): ResponseEntity<ServerResponseDto<KnackFileMorositaDto>> {
        logger.info { "API: GET [/add] called" }
        return ResponseEntity.ok(this.knackMorositaService.add(file))
    }


    @ApiOperation(value = "Richiesta per prendere i dati di un file precedentemente elaborato")
    @GetMapping(value = ["/get/{id}"], produces = ["application/json"])
    fun get(@ApiParam(value = "id") @PathVariable id: String): ResponseEntity<ServerResponseDto<KnackFileMorositaDto>> {
        logger.info { "API: GET [/get/$id] called" }
        return ResponseEntity.ok(this.knackMorositaService.get(id.toLong()))
    }


    @ApiOperation(value = "Richiesta per prendere i dati di un file precedentemente elaborato")
    @GetMapping(value = ["/get/all"], produces = ["application/json"])
    fun all(): ResponseEntity<ServerResponseDto<List<KnackFileMorositaDto>>> {
        logger.info { "API: GET [/get/all] called" }
        return ResponseEntity.ok(this.knackMorositaService.getAll())
    }


    @ApiOperation(value = "Richiesta per prendere i dati di un file precedentemente elaborato")
    @DeleteMapping(value = ["/delete/all"], produces = ["application/json"])
    fun deleteAll(): ResponseEntity<ServerResponseDto<List<KnackFileMorositaDto>>> {
        logger.info { "API: GET [/delete/all] called" }
        return ResponseEntity.ok(this.knackMorositaService.delete())
    }


    @ApiOperation(value = "Richiesta per prendere i dati di un file precedentemente elaborato")
    @DeleteMapping(value = ["/delete/{id}"], produces = ["application/json"])
    fun delete(@ApiParam(value = "id") @PathVariable id: String): ResponseEntity<ServerResponseDto<KnackFileMorositaDto>> {
        logger.info { "API: GET [/delete/$id] called" }
        return ResponseEntity.ok(this.knackMorositaService.delete(id.toLong()))
    }


    @GetMapping("/download/{id}")
    @ResponseBody
    fun download(@PathVariable id: Long): ResponseEntity<ByteArrayResource>? {
        val file = this.knackMorositaService.download(id)
        val inputStream = ByteArrayInputStream(file.file)

        val header = HttpHeaders()
        header.contentType = MediaType("application", "force-download");
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${file.nome}");


        return ResponseEntity.ok().headers(header).body(ByteArrayResource(inputStream.readAllBytes()))
    }
}