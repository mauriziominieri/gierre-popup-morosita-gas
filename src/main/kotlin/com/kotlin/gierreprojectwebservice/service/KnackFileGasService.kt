package com.kotlin.gierreprojectwebservice.service

import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.kotlin.gierreprojectwebservice.dto.KnackFileGasDto
import com.kotlin.gierreprojectwebservice.dto.ServerResponseDto
import com.kotlin.gierreprojectwebservice.entity.KnackFileGas
import com.kotlin.gierreprojectwebservice.exception.EntityNotFoundIdException
import com.kotlin.gierreprojectwebservice.exception.FileExcelException
import com.kotlin.gierreprojectwebservice.deprecated.EmailSender
import com.kotlin.gierreprojectwebservice.helper.ExcelHelper
import com.kotlin.gierreprojectwebservice.helper.KnackExcelGasRow
import com.kotlin.gierreprojectwebservice.mapper.KnackFileGasMapper
import com.kotlin.gierreprojectwebservice.repository.KnackFileGasRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import java.util.logging.Logger

@Service
class KnackFileGasService {

    @Autowired
    private lateinit var knackGasFileRepository: KnackFileGasRepository

    @Autowired
    private lateinit var knackGasFileMapper: KnackFileGasMapper

    @Autowired
    private lateinit var emailSender: EmailSender

    private val excelHelper: ExcelHelper = ExcelHelper()

    private val gsonMapper: Gson = Gson()

    companion object {
        @JvmStatic
        private val logger = Logger.getLogger(KnackFileGasService::class.java.name)
    }

    fun add(file: MultipartFile): ServerResponseDto<KnackFileGasDto> {
        val nomeFile = file.originalFilename ?: "Nome Indefinito - ${LocalDateTime.now()}"

        val gasFile = KnackFileGas(
            id = null,
            nome = nomeFile,
            file = file.inputStream.readAllBytes()
        )

        val jsonFile = this.excelHelper.readFromExcel(file.inputStream, gasFile.nome)
        val nColunms = jsonFile[0].keySet().size

        if(nColunms != 4)
            throw FileExcelException("File Excel non valido. Il numero di colonne richiesto è 4, mentre il file ne contiene: $nColunms")

        val resKnackFile: KnackFileGas

        try {
            resKnackFile = this.knackGasFileRepository.save(gasFile)
            logger.info("KnackFileService.add(file: ${nomeFile}) saved into db.")
        } catch (e: Exception) {
            throw FileExcelException("Salvataggio sul database non riuscito")
        }

        /*
        val itemType = object : TypeToken<List<KnackExcelGasRow>>() {}.type
        val listRow = this.gsonMapper.fromJson<List<KnackExcelGasRow>>(jsonFile.toString(), itemType)

        listRow.map { gasExcelRow ->

            val knackRow = KnackGasNonLavorabile(id = null, denominazioneComune = gasExcelRow.denominazioneComune, denominazioneCityGate = gasExcelRow.denominazioneCityGate, resKnackFile)
            resKnackFile.listGasNonLavorabili.add(knackRow)
        }

        this.knackGasFileRepository.save(resKnackFile);*/

        return ServerResponseDto.ok(this.knackGasFileMapper.toDto(resKnackFile))
    }

    fun get(id: Long): ServerResponseDto<KnackFileGasDto> {
        val knackFile =
            this.knackGasFileRepository.findById(id).orElseThrow { EntityNotFoundIdException("FileExcel", id) }
        return ServerResponseDto.ok(this.knackGasFileMapper.toDto(knackFile))
    }

    fun getAll(): ServerResponseDto<List<KnackFileGasDto>> {
        val list = this.knackGasFileRepository.findAll()
        return ServerResponseDto.ok(this.knackGasFileMapper.toDtos(list))
    }

    fun delete(id: Long): ServerResponseDto<KnackFileGasDto> {
        val knackFile =
            this.knackGasFileRepository.findById(id).orElseThrow { EntityNotFoundIdException("FileExcel", id) }
        this.knackGasFileRepository.delete(knackFile)
        return ServerResponseDto.ok(this.knackGasFileMapper.toDto(knackFile))
    }

    fun delete(): ServerResponseDto<List<KnackFileGasDto>> {
        val list = this.knackGasFileRepository.findAll()
        this.knackGasFileRepository.deleteAll(list)
        return ServerResponseDto.ok(this.knackGasFileMapper.toDtos(list))
    }

    fun download(id: Long): KnackFileGas {
        return this.knackGasFileRepository.findById(id).orElseThrow { EntityNotFoundIdException("FileExcel", id) }
    }
}