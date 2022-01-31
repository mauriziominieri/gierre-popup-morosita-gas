package com.kotlin.gierreprojectwebservice.helper

import com.kotlin.gierreprojectwebservice.exception.FileExcelException
import org.apache.commons.io.output.ByteArrayOutputStream
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.json.JSONObject
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


class ExcelHelper {

    fun readFromExcel(file: InputStream, filename: String): List<JSONObject> {

        val xlWb: Workbook

        try {
            xlWb = WorkbookFactory.create(file)
        }
        catch (e: IOException) {
            throw FileExcelException("File: [${filename}] non valid.")
        }

        val xlWs = xlWb.getSheetAt(0)
        val maxRow = xlWs.lastRowNum

        val headers = this.getHeaders(xlWs)

        val jsonData: MutableList<JSONObject> = mutableListOf()

        for (i in 1..maxRow) {
            jsonData.add(this.getRowData(xlWs.getRow(i), headers))
        }

        return jsonData
    }


    private fun getHeaders(sheet: Sheet): List<String> {
        return sheet.getRow(0).cellIterator().asSequence().toList().map { it.stringCellValue }
    }


    private fun getRowData(row: Row, headers: List<String>): JSONObject {

        if(headers.size != row.lastCellNum.toInt())
            return JSONObject() // lanciare errore

        val data = JSONObject()

        val formatter = DataFormatter()

        for(i in 0 until row.lastCellNum) {
            val cellValue = formatter.formatCellValue(row.getCell(i))
            data.put(headers[i], cellValue)
        }

        return data
    }


    // pod ->
   //  pdr ->
     fun writeExcelPopup(data: List<KnackEsitoPopupExcelRow>): ByteArrayOutputStream {

        val xlWb = XSSFWorkbook()
        val xlWs = xlWb.createSheet()

        val headerRow = xlWs.createRow(0)

        val headers = listOf<String>("DATA CREAZIONE", "POD", "PDR", "POPUP POD", "POPUP PDR")

        for(i in headers.indices) {
            headerRow.createCell(i).setCellValue(headers[i])
        }


        data.forEachIndexed { i, rowData ->

            val row = xlWs.createRow(i + 1)

            for(j in headers.indices) {
                when(j) {
                    0 -> row.createCell(j).setCellValue(rowData.data_creazione)
                    1 -> row.createCell(j).setCellValue(rowData.pod)
                    2 -> row.createCell(j).setCellValue(rowData.pdr)
                    3 -> row.createCell(j).setCellValue(rowData.check_popup_pod)
                    4 -> row.createCell(j).setCellValue(rowData.check_popup_pdr)
                }
            }
        }


        val bos = ByteArrayOutputStream()
        xlWb.write(bos)
        bos.close()

        return bos
    }




    fun writeExcelMorosita(data: List<KnackEsitoMorositaExcelRow>): ByteArrayOutputStream {

        val xlWb = XSSFWorkbook()
        val xlWs = xlWb.createSheet()

        val headerRow = xlWs.createRow(0)

        val headers = listOf<String>("POD/PDR", "MOROSITA_ESITO")

        for(i in headers.indices) {
            headerRow.createCell(i).setCellValue(headers[i])
        }


        data.forEachIndexed { i, rowData ->

            val row = xlWs.createRow(i + 1)

            for(j in headers.indices) {
                when(j) {
                    0 -> row.createCell(j).setCellValue(rowData.podpdr)
                    1 -> row.createCell(j).setCellValue(rowData.esito)
                }
            }
        }


        val bos = ByteArrayOutputStream()
        xlWb.write(bos)
        bos.close()

        return bos
    }

}
