package com.kotlin.gierreprojectwebservice.service

import com.kotlin.gierreprojectwebservice.dto.KnackFileMorositaDto
import com.kotlin.gierreprojectwebservice.dto.ServerResponseDto
import com.kotlin.gierreprojectwebservice.entity.KnackFileMorosita
import com.kotlin.gierreprojectwebservice.exception.EntityNotFoundIdException
import com.kotlin.gierreprojectwebservice.exception.FileExcelException
import com.kotlin.gierreprojectwebservice.helper.ExcelHelper
import com.kotlin.gierreprojectwebservice.mapper.KnackFileMorositaMapper
import com.kotlin.gierreprojectwebservice.repository.KnackFileMorositaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import java.util.logging.Logger

@Service
class KnackFileMorositaService {

    @Autowired
    private lateinit var knackMorositaFileRepository: KnackFileMorositaRepository

    @Autowired
    private lateinit var knackMorositaFileMapper: KnackFileMorositaMapper

    private val excelHelper: ExcelHelper = ExcelHelper()

    companion object {
        @JvmStatic
        private val logger = Logger.getLogger(KnackFileMorositaService::class.java.name)
    }

    fun add(file: MultipartFile): ServerResponseDto<KnackFileMorositaDto> {
        val nomeFile = file.originalFilename ?: "Nome Indefinito - ${LocalDateTime.now()}"

        val morositaFile = KnackFileMorosita(
            id = null,
            nome = nomeFile,
            file = file.inputStream.readAllBytes(),
        )

        val jsonFile = this.excelHelper.readFromExcel(file.inputStream, morositaFile.nome)
        val columns = jsonFile[0].keySet()

        if(columns.size != 1)
            throw FileExcelException("File Excel non valido. Il numero di colonne richiesto è 1, mentre il file ne contiene: ${columns.size}")

        if(!columns.contains("POD/PDR"))
            throw FileExcelException("File Excel non valido. Non contiene la colonna \"POD/PDR\"")


        val resKnackFile: KnackFileMorosita

        try {
            resKnackFile = this.knackMorositaFileRepository.save(morositaFile)
            logger.info("KnackFileService.add(file: ${nomeFile}) saved into db.")
        } catch (e: Exception) {
            throw FileExcelException("Salvataggio sul database non riuscito")
        }

        /*val itemType = object : TypeToken<List<KnackExcelMorositaRow>>() {}.type
        val listRow = this.gsonMapper.fromJson<List<KnackExcelMorositaRow>>(jsonFile.toString(), itemType)


        listRow.map { morositaExcelRow ->

            if(morositaExcelRow.podpdr.isNotEmpty()) {
                val type = if(morositaExcelRow.podpdr.contains("IT")) "POD" else "PDR"
                val knackRow = KnackMorositaNonLavorabile(id = null, type = type, data = morositaExcelRow.podpdr, resKnackFile)
                resKnackFile.listMorositaNonLavorabili.add(knackRow)
            }

        }

        this.knackMorositaFileRepository.save(resKnackFile);*/

        return ServerResponseDto.ok(this.knackMorositaFileMapper.toDto(resKnackFile))
    }


    fun get(id: Long): ServerResponseDto<KnackFileMorositaDto> {
        val knackFile =
            this.knackMorositaFileRepository.findById(id).orElseThrow { EntityNotFoundIdException("FileExcel", id) }
        return ServerResponseDto.ok(this.knackMorositaFileMapper.toDto(knackFile))
    }


    fun getAll(): ServerResponseDto<List<KnackFileMorositaDto>> {
        val list = this.knackMorositaFileRepository.findAll()
        return ServerResponseDto.ok(this.knackMorositaFileMapper.toDtos(list))
    }


    fun delete(id: Long): ServerResponseDto<KnackFileMorositaDto> {
        val knackFile =
            this.knackMorositaFileRepository.findById(id).orElseThrow { EntityNotFoundIdException("FileExcel", id) }
        this.knackMorositaFileRepository.delete(knackFile)
        return ServerResponseDto.ok(this.knackMorositaFileMapper.toDto(knackFile))
    }


    fun delete(): ServerResponseDto<List<KnackFileMorositaDto>> {
        val list = this.knackMorositaFileRepository.findAll()
        this.knackMorositaFileRepository.deleteAll(list)
        return ServerResponseDto.ok(this.knackMorositaFileMapper.toDtos(list))
    }


    fun download(id: Long): KnackFileMorosita {
        return this.knackMorositaFileRepository.findById(id).orElseThrow { EntityNotFoundIdException("FileExcel", id) }
    }

}