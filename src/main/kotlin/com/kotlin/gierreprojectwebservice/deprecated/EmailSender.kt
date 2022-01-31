package com.kotlin.gierreprojectwebservice.deprecated

import org.apache.commons.io.output.ByteArrayOutputStream
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import java.util.logging.Logger
import javax.mail.internet.MimeMessage
import javax.mail.util.ByteArrayDataSource

@Component
class EmailSender() {

    @Autowired
    private lateinit var emailSender: JavaMailSender


    companion object {
        @JvmStatic private val logger = Logger.getLogger(EmailSender::class.java.name)
    }

    fun sendFile(to: String, subject: String, text: String, file: ByteArrayOutputStream) {

        val message: MimeMessage = this.emailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)
        helper.setTo(to)
        helper.setSubject(subject)
        helper.setText(text)

        val fds: ByteArrayDataSource = ByteArrayDataSource(file.toByteArray(), "application/vnd.ms-excel")
        helper.addAttachment("Risultato.xlsx", fds)

        this.emailSender.send(message)

        logger.info { "send() email sent to $to" }
    }


    fun send(to: String, subject: String, text: String) {
        val message: MimeMessage = this.emailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)
        helper.setTo(to)
        helper.setSubject(subject)
        helper.setText(text)

        this.emailSender.send(message)

        logger.info { "send() email sent to $to" }
    }
}