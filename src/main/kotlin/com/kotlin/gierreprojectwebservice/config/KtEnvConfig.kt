package com.kotlin.gierreprojectwebservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.net.InetAddress

@Configuration
open class KtEnvConfig {

    @Value("\${KtEnvConfig.knack.popup-pod-url}")
    lateinit var _KNACK_POPUP_POD_URL: String

    @Value("\${KtEnvConfig.knack.popup-pdr-url}")
    lateinit var _KNACK_POPUP_PDR_URL: String

    @Value("\${KtEnvConfig.knack.popup-morosita-url}")
    lateinit var _KNACK_POPUP_MOROSITA_URL: String

    @Value("\${KtEnvConfig.knack.controllo-gas-url}")
    lateinit var _KNACK_CONTROLLO_GAS_URL: String

    @Value("\${KtEnvConfig.system.env}")
    lateinit var env: String

    @Value("\${KtEnvConfig.system.prod-url}")
    lateinit var prodServerUrl: String

    @Value("\${KtEnvConfig.system.local-url}")
    lateinit var localServerUrl: String
}