package com.kotlin.gierreprojectwebservice.service

import com.google.gson.Gson
import com.kotlin.gierreprojectwebservice.dto.KnackFilePopupDto
import com.kotlin.gierreprojectwebservice.dto.ServerResponseDto
import com.kotlin.gierreprojectwebservice.entity.KnackFilePopup
import com.kotlin.gierreprojectwebservice.exception.EntityNotFoundIdException
import com.kotlin.gierreprojectwebservice.exception.FileExcelException
import com.kotlin.gierreprojectwebservice.helper.*
import com.kotlin.gierreprojectwebservice.mapper.KnackFilePopupMapper
import com.kotlin.gierreprojectwebservice.repository.KnackFilePopupRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import java.util.logging.Logger

@Service
class KnackFilePopupService {

    @Autowired
    private lateinit var knackPopupFileRepository: KnackFilePopupRepository

    @Autowired
    private lateinit var knackPopupFileMapper: KnackFilePopupMapper

    private val excelHelper: ExcelHelper = ExcelHelper()


    companion object {
        @JvmStatic
        private val logger = Logger.getLogger(KnackFilePopupService::class.java.name)
    }



    fun add(file: MultipartFile): ServerResponseDto<KnackFilePopupDto> {
        val nomeFile = file.originalFilename ?: "Nome Indefinito - ${LocalDateTime.now()}"

        val popupFile = KnackFilePopup(
            id = null,
            nome = nomeFile,
            file = file.inputStream.readAllBytes()
        )

        val jsonFile = this@KnackFilePopupService.excelHelper.readFromExcel(file.inputStream, popupFile.nome)
        val columns = jsonFile[0].keySet()

        if(columns.size != 3)
            throw FileExcelException("File Excel non valido. Il numero di colonne richiesto è 3, mentre il file ne contiene: ${columns.size}")

        if(!columns.contains("POD"))
            throw FileExcelException("File Excel non valido. Non contiene la colonna \"POD\"")

        if(!columns.contains("PDR"))
            throw FileExcelException("File Excel non valido. Non contiene la colonna \"PDR\"")

        val resKnackFile: KnackFilePopup

        try {
            resKnackFile = this.knackPopupFileRepository.save(popupFile)
            logger.info("KnackFileService.add(file: ${nomeFile}) saved into db.")
        } catch (e: Exception) {
            throw FileExcelException("Salvataggio sul database non riuscito")
        }

        /*val itemType = object : TypeToken<List<KnackExcelPopupRow>>() {}.type
        val listRow =
            this@KnackFilePopupService.gsonMapper.fromJson<List<KnackExcelPopupRow>>(jsonFile.toString(), itemType)

        logger.info("KnackFileService.add(file: ${nomeFile}) parsed to Object array, size: ${listRow.size}")

        listRow.map { popupExcelRow ->
            if (popupExcelRow.pod != null && popupExcelRow.pod.isNotEmpty()) {
                val knackRow = KnackPopupNonLavorabile(type = "POD", data = popupExcelRow.pod, file = resKnackFile)
                try {
                    this.knackPopupNonLavorabileRepository.save(knackRow)
                    logger.info("KnackFileService.add(file: ${nomeFile}): data: ${knackRow.data} saved on db")

                } catch (e: Exception) {
                    logger.info("KnackFileService.add(file: ${nomeFile}): ERROR on insert data: ${knackRow.data} => ${e.localizedMessage}")
                }
                //resKnackFile.listPopupNonLavorabili.add(knackRow)
            }

            if (popupExcelRow.pdr != null && popupExcelRow.pdr.isNotEmpty()) {
                val knackRow = KnackPopupNonLavorabile(type = "PDR", data = popupExcelRow.pdr, file = resKnackFile)

                try {
                    this.knackPopupNonLavorabileRepository.save(knackRow)
                    logger.info("KnackFileService.add(file: ${nomeFile}): data: ${knackRow.data} saved on db")
                } catch (e: Exception) {
                    logger.info("KnackFileService.add(file: ${nomeFile}): ERROR on insert data: ${knackRow.data} => ${e.localizedMessage}")
                }

                //resKnackFile.listPopupNonLavorabili.add(knackRow)
            }
        }

        //this.knackPopupFileRepository.save(resKnackFile);*/

        return ServerResponseDto.ok(this.knackPopupFileMapper.toDto(resKnackFile))
    }


    fun get(id: Long): ServerResponseDto<KnackFilePopupDto> {
        val knackFile =
            this.knackPopupFileRepository.findById(id).orElseThrow { EntityNotFoundIdException("FileExcel", id) }
        return ServerResponseDto.ok(this.knackPopupFileMapper.toDto(knackFile))
    }


    fun getAll(): ServerResponseDto<List<KnackFilePopupDto>> {
        val list = this.knackPopupFileRepository.findAll()
        return ServerResponseDto.ok(this.knackPopupFileMapper.toDtos(list))
    }


    fun delete(id: Long): ServerResponseDto<KnackFilePopupDto> {
        val knackFile =
            this.knackPopupFileRepository.findById(id).orElseThrow { EntityNotFoundIdException("FileExcel", id) }
        this.knackPopupFileRepository.delete(knackFile)
        return ServerResponseDto.ok(this.knackPopupFileMapper.toDto(knackFile))
    }


    fun delete(): ServerResponseDto<List<KnackFilePopupDto>> {
        val list = this.knackPopupFileRepository.findAll()
        this.knackPopupFileRepository.deleteAll(list)
        return ServerResponseDto.ok(this.knackPopupFileMapper.toDtos(list))
    }


    fun download(id: Long): KnackFilePopup {
        return this.knackPopupFileRepository.findById(id).orElseThrow { EntityNotFoundIdException("FileExcel", id) }
    }

}







/*    fun load(id: Long, email: String): ServerResponseDto<KnackFilePopupDto> {
        val knackFile = this.knackPopupFileRepository.findById(id).orElseThrow { EntityNotFoundIdException("FileExcel", id) }
        val inputStreamFile = ByteArrayInputStream(knackFile.file)


        GlobalScope.async {
            withContext(Dispatchers.Default) {

                val jsonFile: List<JSONObject>

                val time = measureTimeMillis {
                     jsonFile = this@KnackFileService.excelHelper.readFromExcel(inputStreamFile, knackFile.nome)

                    val typeFile = jsonFile[0].keySet().contains("POD/PDR")

                    val file = if (typeFile) { // 1 colonna

                        val itemType = object : TypeToken<List<KnackExcelMorositaRow>>() {}.type
                        val listRow = this@KnackFileService.gsonMapper.fromJson<List<KnackExcelMorositaRow>>(
                            jsonFile.toString(),
                            itemType
                        )
                        val esitoList = this@KnackFileService.callRoutineCheckMorosita(listRow)

                        this@KnackFileService.excelHelper.writeExcelMorosita(esitoList)

                    }
                    else { // 3 colonne
                        val itemType = object : TypeToken<List<KnackExcelPopupRow>>() {}.type
                        val listRow = this@KnackFileService.gsonMapper.fromJson<List<KnackExcelPopupRow>>(
                            jsonFile.toString(),
                            itemType
                        )
                        val esitoList = this@KnackFileService.callRoutineCheckPopup(listRow)

                        this@KnackFileService.excelHelper.writeExcelPopup(esitoList)
                    }


                    this@KnackFileService.emailSender.sendFile(
                        to = email,
                        subject = "[Gierre] PDR-POD-MOROSITA Platform: Comunicazione fine processamento file Excel",
                        text = "Salve $email,\n\nla ringraziamo per aver utilizzato la piattaforma PDR-POD-MOROSITA, in allegato troverà l'elaborazione del file: ${knackFile.nome} terminata il ${
                            LocalDateTime.now(
                                ZoneId.of("Europe/Rome")
                            )
                        }.\n\nCordiali Saluti,\nGIERRE-PDR-POD-MOROSITA Platform.\n\n\n\n",
                        file = file
                    )

                    knackFile.status = "PROCESSATO CORRETTAMENTE"
                    this@KnackFileService.knackFileRepository.save(knackFile)
                }

                logger.info("KnackFileService.load(fileId: $id) request from $email finished in $time ms")
            }
        }

        return ServerResponseDto.ok(this.knackFileMapper.toDto(knackFile))
    }



    private fun callRoutineCheckPopup(list: List<KnackExcelPopupRow>): List<KnackEsitoPopupExcelRow> {

        val resList: MutableList<KnackEsitoPopupExcelRow> = mutableListOf()

        val time = measureTimeMillis {

            list.forEach {
                try {
                    val res = KnackEsitoPopupExcelRow()

                    if (it.pod != null && it.pod.isNotEmpty()) {
                        val esitoPod = this.knackService.checkPod(it.pod, "", false).data

                        res.pod = it.pod
                        res.data_creazione = it.createdAt
                        res.check_popup_pod = esitoPod.toString()
                    }

                    if (it.pdr != null && it.pdr.isNotEmpty()) {
                        val esitoPdr = this.knackService.checkPdr(it.pdr, "", false).data

                        res.pdr = it.pdr
                        res.data_creazione = it.createdAt
                        res.check_popup_pdr = esitoPdr.toString()
                    }

                    resList.add(res)
                } catch (e: Exception) {
                    logger.severe("KnackFileService.callRoutineCheckPopup() error: ${e.localizedMessage}")
                }
            }
        }

        logger.info("KnackFileService.callRoutineCheckPopup() finished in $time, processed ${resList.size} rows")

        return resList
    }



    private fun callRoutineCheckMorosita(list: List<KnackExcelMorositaRow>): List<KnackEsitoMorositaExcelRow> {
        val resList: MutableList<KnackEsitoMorositaExcelRow> = mutableListOf()

        val time = measureTimeMillis {
            list.forEach {
                try {
                    val esito = this.knackService.checkMorosita(it.podpdr, null, "", false).data

                    val data = KnackEsitoMorositaExcelRow()
                    data.podpdr = it.podpdr
                    data.esito = esito

                    resList.add(data)
                }
                catch (e: Exception) {
                    logger.severe("KnackFileService.callRoutineCheckMorosita() error: ${e.localizedMessage}")
                }
            }
        }

        logger.info("KnackFileService.callRoutineCheckMorosita() finished in $time, processed ${resList.size} rows")

        return resList
    }
    */