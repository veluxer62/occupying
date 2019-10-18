package com.kh.api

import com.kh.occupying.Korail
import com.kh.occupying.KorailProperties
import com.kh.occupying.WebClientWrapper
import com.kh.service.AlarmSender
import com.kh.util.SecretProperties
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
        val secretProperties = SecretProperties()
        val mailSender = JavaMailSenderImpl()
        mailSender.host = "smtp.gmail.com"
        mailSender.port = 587
        mailSender.username = secretProperties.email.id
        mailSender.password = secretProperties.email.pw

        val props = mailSender.javaMailProperties
        props["mail.transport.protocol"] = "smtp"
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"
        return mailSender
    }

    @Bean
    fun alarmSender() = AlarmSender(getJavaMailSender())

}