package com.kotlin.gierreprojectwebservice.deprecated

import org.json.JSONObject
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import java.util.logging.Logger

class HttpClientHelper(
    private var headers: List<Pair<String, String>> = emptyList(),
    private var params: List<Pair<String, String>> = emptyList(),
    private var bodyData: JSONObject = JSONObject()
) {

    private lateinit var request: HttpRequest.Builder

    private val TIMEOUT: Long = 5 //secondi

    companion object {
        private val LOG = Logger.getLogger(HttpClientHelper::class.java.name)
        private val client = HttpClient.newBuilder().build();

        fun factory(): HttpClientHelper {
            return HttpClientHelper()
        }
    }


    fun addHeaders(headers: List<Pair<String, String>>): HttpClientHelper {
        this.headers = headers
        return this
    }


    fun addParams(params: List<Pair<String, String>> = emptyList()): HttpClientHelper {
        this.params = params
        return this
    }


    fun createRequest(endpoint: String, callType: String): HttpClientHelper {

        var url = endpoint

        if(this.params.isNotEmpty())
            url = url + "?" + this.getQueryParams()

         this.request = HttpRequest
            .newBuilder()
            .uri(URI.create(url))
            .timeout(Duration.ofSeconds(this.TIMEOUT))


        when(callType) {
            "POST" -> {
                this.request.POST(HttpRequest.BodyPublishers.ofString(this.bodyData.toString()))
            }
            "GET" -> {
                this.request.GET()
            }
        }

        this.headers.forEach { this.request.setHeader(it.first, it.second) }

        return this
    }


    fun call(): HttpResponse<String> {
        return client.send(this.request.build(), HttpResponse.BodyHandlers.ofString())
    }


    private fun getQueryParams(): String {

        var query = ""

        for (i in this.params.indices) {
            query += "${this.params[i].first}=" + URLEncoder.encode(this.params[i].second, "UTF-8")

            if(i < this.params.size-1)
                query += "&"
        }

        return query
    }

}