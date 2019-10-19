package com.kh.api.config

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

object NaverMailSenderConfig {
    fun getSender(id: String, pw: String): JavaMailSender {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = "smtp.naver.com"
        mailSender.port = 465
        mailSender.username = id
        mailSender.password = pw

        val props = mailSender.javaMailProperties
        props["mail.transport.protocol"] = "smtp"
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"
        props["mail.smtp.ssl.enable"] = "true"
        return mailSender
    }
}