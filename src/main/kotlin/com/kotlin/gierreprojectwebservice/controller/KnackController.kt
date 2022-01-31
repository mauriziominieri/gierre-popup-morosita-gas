package com.kotlin.gierreprojectwebservice.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping


@Controller
class KnackController {

    @GetMapping(value = ["/", "/dashboard"])
    fun dashboard(model: Model): String {
        return "index"
    }

    @GetMapping(value = ["/ricerche"])
    fun ricerche(model: Model): String {
        return "ricerche"
    }

    @GetMapping(value = ["/archivioFilePodPdr"])
    fun archivioFilePodPdr(model: Model): String {
        return "archivioFilePodPdr"
    }

    @GetMapping(value = ["/archivioFileMorosita"])
    fun archivioFileMorosita(model: Model): String {
        return "archivioFileMorosita"
    }

    @GetMapping(value = ["/archivioFileGas"])
    fun archivioFileGas(model: Model): String {
        return "archivioFileGas"
    }

    @GetMapping(value = ["/impostazioni"])
    fun impostazioni(model: Model): String {
        return "impostazioni"
    }

    @GetMapping(value = ["/login", "/logout"])
    fun login(model: Model): String {
        return "login"
    }

    /*@GetMapping(value = ["/signup/{email}"])
    fun signup(@PathVariable email: String, model: Model): String {
        model.addAttribute("email", email)
        return "login"
    }*/
}
