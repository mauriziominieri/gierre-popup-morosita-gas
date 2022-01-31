package com.kotlin.gierreprojectwebservice.service

import com.kotlin.gierreprojectwebservice.dto.*
import com.kotlin.gierreprojectwebservice.entity.KnackRicerca
import com.kotlin.gierreprojectwebservice.helper.ExcelHelper
import com.kotlin.gierreprojectwebservice.mapper.KnackRicercaMapper
import com.kotlin.gierreprojectwebservice.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream
import java.util.logging.Logger


@Service
class KnackServiceImpl(
    @Autowired private val knackFilePopupRepository: KnackFilePopupRepository,
    @Autowired private val knackFileMorositaRepository: KnackFileMorositaRepository,
    @Autowired private val knackFileGasRepository: KnackFileGasRepository,
    @Autowired private val knackRicercaRepository: KnackRicercaRepository,
    @Autowired private val knackRicercaMapper: KnackRicercaMapper,
): KnackService {
    private val excelHelper: ExcelHelper = ExcelHelper()

    companion object {
        @JvmStatic private val logger = Logger.getLogger(KnackServiceImpl::class.java.name)
    }

    override fun checkPod(pod: String, email: String): ServerResponseDto<String> {

        val files = this.knackFilePopupRepository.findAll()
        var esito = "Lavorabile"

        files.forEach loopFiles@ {
            val jsonFile = this.excelHelper.readFromExcel(ByteArrayInputStream(it.file), it.nome)

            jsonFile.forEach{ itRow ->
                val podValue = itRow.getString("POD").uppercase()
                if(podValue.isNotEmpty() && podValue.equals(pod.uppercase())) {
                    esito = "Non Lavorabile"
                    return@loopFiles;
                }
            }
        }

        this.knackRicercaRepository.save(KnackRicerca(id = null, tipo = "POD", valore = pod, operatore = email, esito = esito))
        return ServerResponseDto.ok(esito)
    }

    override fun checkPdr(pdr: String, email: String): ServerResponseDto<String> {
        val files = this.knackFilePopupRepository.findAll()
        var esito = "Lavorabile"

        files.forEach loopFiles@ {
            val jsonFile = this.excelHelper.readFromExcel(ByteArrayInputStream(it.file), it.nome)

            jsonFile.forEach{ itRow ->
                val pdrValue = itRow.getString("PDR").uppercase()
                if(pdrValue.isNotEmpty() && pdrValue.equals(pdr.uppercase())) {
                    esito = "Non Lavorabile"
                    return@loopFiles;
                }
            }
        }

        this.knackRicercaRepository.save(KnackRicerca(id = null, tipo = "PDR", valore = pdr, operatore = email, esito = esito))
        return ServerResponseDto.ok(esito)
    }

    override fun checkMorosita(data: String, type: String, email: String): ServerResponseDto<String> {

        val files = this.knackFileMorositaRepository.findAll()
        var esito = "Lavorabile"

        files.forEach loopFiles@ {
            val jsonFile = this.excelHelper.readFromExcel(ByteArrayInputStream(it.file), it.nome)

            jsonFile.forEach{ itRow ->
                val value = itRow.getString("POD/PDR").uppercase()
                if(value.isNotEmpty() && value.equals(data.uppercase())) {
                    esito = "Non Lavorabile"
                    return@loopFiles;
                }
            }
        }

        this.knackRicercaRepository.save(KnackRicerca(id = null, tipo = "MOROSITA:${type.uppercase()}", valore = data, operatore = email, esito = esito))
        return ServerResponseDto.ok(esito)
    }

    override fun checkGas(comune: String, email: String): ServerResponseDto<List<KnackControlloGasResponseDto>> {
        val files = this.knackFileGasRepository.findAll()
        val gasMap = mutableMapOf<String, String>()

        files.forEach {
            val jsonFile = this.excelHelper.readFromExcel(ByteArrayInputStream(it.file), it.nome)

            jsonFile.forEach{ itRow ->
                val denominazioneComune = itRow.getString("Denominazione Comune").lowercase()
                val remi = itRow.getString("REMI").lowercase()
                val lavorabilita = itRow.getString("LAVORABILITA PLT").lowercase()

                if(denominazioneComune.isNotEmpty() && remi.isNotEmpty() && lavorabilita.isNotEmpty() && denominazioneComune.equals(comune.lowercase())) {
                  gasMap.put(remi.uppercase(), if(lavorabilita.equals("si")) "Lavorabile" else "Non Lavorabile")
                }
            }
        }

        val res = gasMap.map{ KnackControlloGasResponseDto(comune, it.key, it.value) }

        if(res.size == 0)
            this.knackRicercaRepository.save(KnackRicerca(id = null, tipo = "GAS", valore = comune.uppercase(), operatore = email, esito = "Non Trovato"))

        res.forEach {
            this.knackRicercaRepository.save(KnackRicerca(id = null, tipo = "GAS", valore = "${it.citta} : ${it.remi}", operatore = email, esito = it.vendibilita))
        }
        return ServerResponseDto.ok(res)
    }

    override fun get(): ServerResponseDto<List<KnackRicercaDto>> {
        return ServerResponseDto.ok(this.knackRicercaMapper.toDtos(this.knackRicercaRepository.findAll()))
    }
}







/*
*           DEPRECATO!
*
*     /*override fun checkPod(pod: String?, email: String,  toSave: Boolean): ServerResponseDto<List<String>> {

        var resData: List<String> = emptyList()

        val time = measureTimeMillis {

            if(pod == null || pod.isBlank())
                return ServerResponseDto.ok(resData)

            //logger.info { "checkPod(pdr: $pod) started..." }

            val response: HttpResponse<String>

            try {
                response = this.knackHelperFactory.createKnackHttpRequest(
                    url = this.ktEnvConfig._KNACK_POPUP_POD_URL,
                    data = listOf(pod),
                    type = KnackHelperFactory.KnackCall.KnackPod()
                ).call()
            }
            catch (e: Exception) {
                logger.severe("checkPod(pdr: ${pod}) error ${e.localizedMessage}")
                return ServerResponseDto.ok(listOf("Errore di connessione"))
            }

            //logger.info { "checkPod(pod: ${pod}) response returned => status: ${response.statusCode()}, data: ${response.body()}" }

            val result: KnackResponsePodDto = this.jsonSerializer.decodeFromString<KnackResponsePodDto>(response.body())

            resData = if(result.total_records == 0) {
                listOf("Lavorabile")
            } else
                this.createResult(result.records.map { it.field_25_raw.filename })

            if(toSave) {
                val u = KnackUtenza(id = null, data = pod, operatore = email, type = "POD", checkPopup = resData.toString(), createdAt = LocalDateTime.now(ZoneId.of("Europe/Rome")), dataInserimento = LocalDateTime.now(ZoneId.of("Europe/Rome")).toString())
                this.knackUtenzaRepository.save(u)
            }
        }

        logger.info { "checkPod(pod: ${pod}) finished in $time ms, result data = ${resData.toString()}" }

        return ServerResponseDto.ok(resData)
    }




    override fun checkPdr(pdr: String?, email: String, toSave: Boolean): ServerResponseDto<List<String>> {

        var resData: List<String> = emptyList()

        val time = measureTimeMillis {

            if(pdr == null || pdr.isBlank())
                return ServerResponseDto.ok(resData)

            //logger.info { "checkPdr(pdr: $pdr) started..." }

            val response: HttpResponse<String>

            try {
                response = this.knackHelperFactory.createKnackHttpRequest(
                    url = this.ktEnvConfig._KNACK_POPUP_PDR_URL,
                    data = listOf(pdr),
                    type = KnackHelperFactory.KnackCall.KnackPdr()
                ).call()
            }
            catch (e: Exception) {
                logger.severe("checkPdr(pdr: ${pdr}) error ${e.localizedMessage}")
                return ServerResponseDto.ok(listOf("Errore di connessione"))
            }

            //logger.info { "checkPdr(pdr: ${pdr}) response returned => status: ${response.statusCode()}, data: ${response.body()}" }

            val result: KnackResponsePdrDto = this.jsonSerializer.decodeFromString<KnackResponsePdrDto>(response.body())

            //logger.info { "checkPdr(pdr: ${pdr}) result data = ${result.records.toString()}" }

            resData = if(result.total_records == 0) {
                listOf("Lavorabile")
            } else {
                this.createResult(result.records.map { it.field_26_raw.filename })
            }

            if(toSave) {
                val u = KnackUtenza(id = null, data = pdr, operatore = email, type = "PDR", checkPopup = resData.toString(), createdAt = LocalDateTime.now(ZoneId.of("Europe/Rome")), dataInserimento = LocalDateTime.now(ZoneId.of("Europe/Rome")).toString())
                this.knackUtenzaRepository.save(u)
            }
        }

        logger.info { "checkPdr(pdr: ${pdr}) finished in $time ms, result data = ${resData.toString()}" }

        return ServerResponseDto.ok(resData)
    }





    override fun checkMorosita(data: String?, type: String?, email: String, toSave: Boolean): ServerResponseDto<String> {

        var esito = ""

        val time = measureTimeMillis {

            if(data == null || data.isBlank())
                return ServerResponseDto.ok(esito)

            //logger.info { "checkMorosita(data: ${data}) started..."}

            val response: HttpResponse<String>

            try {
                 response = this.knackHelperFactory.createKnackHttpRequest(
                    url = this.ktEnvConfig._KNACK_POPUP_MOROSITA_URL,
                    data = listOf(data),
                    type = KnackHelperFactory.KnackCall.KnackMorosita()
                ).call()
            }
            catch (e:Exception) {
                logger.severe("checkMorosita(data: ${data}) error ${e.localizedMessage}")
                return ServerResponseDto.ok("Errore di connessione")
            }

            val result: KnackResponseMorositaDto = this.jsonSerializer.decodeFromString<KnackResponseMorositaDto>(response.body())

            esito = if(result.total_records == 0) "Lavorabile" else "Non Lavorabile"

            if(toSave) {
                this.knackUtenzaRepository.save(
                    KnackUtenza(
                        id = null,
                        data = data,
                        operatore = email,
                        type = type ?: "?",
                        checkPopup = "",
                        morosita = esito,
                        createdAt = LocalDateTime.now(ZoneId.of("Europe/Rome")),
                        dataInserimento = LocalDateTime.now(ZoneId.of("Europe/Rome")).toString()
                    )
                )
            }
        }

        logger.info { "checkMorosita(data: ${data}) finished in $time ms, esito = $esito"}

        return ServerResponseDto.ok(esito)
    }



    override fun checkGas(citta: List<String>): ServerResponseDto<List<KnackControlloGasDto>> {

        var res: List<KnackControlloGasDto> = emptyList()

        val time = measureTimeMillis {
            val response = this.knackHelperFactory.createKnackHttpRequest(
                url = this.ktEnvConfig._KNACK_CONTROLLO_GAS_URL,
                data = citta,
                type = KnackHelperFactory.KnackCall.KnackControlloGas()
            ).call()

            val result: KnackResponseControlloGasDto = this.jsonSerializer.decodeFromString<KnackResponseControlloGasDto>(response.body())

            res = result.records.map { KnackControlloGasDto(citta = it.field_2, codiceRemi = it.field_12, esito = it.field_11_raw, img = it.field_9) }
        }

        return ServerResponseDto.ok(res)
    }


    private fun createResult(resultKnack: List<String>): List<String> {

        val result = mutableListOf<String>()

        resultKnack.forEach {
            var esito = "Errore di Elaborazione"

            if(it == "dd.jpg")
                esito = "Non Lavorabile"
            else if(it == "OK.jpg")
                esito = "Lavorabile"

            result.add(esito)
        }

        return result
    }




    override fun get(onlyHuman: Boolean): ServerResponseDto<List<KnackRicercaDto>> {
        return when(onlyHuman) {
            true -> ServerResponseDto.ok(this.ricercheOnlyHuman())
            false -> ServerResponseDto.ok(emptyList()) // not implemeted
        }
    }
    */
*
*
* */