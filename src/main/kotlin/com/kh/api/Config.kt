package com.kh.api

import com.kh.occupying.Korail
import com.kh.occupying.KorailProperties
import com.kh.occupying.WebClientWrapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
class Config {

    @Bean
    fun korail() = Korail(WebClientWrapper(KorailProperties()))

    @Bean
    fun getJavaMailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = "smtp.gmail.com"
        mailSender.port = 587
        return mailSender
    }

}