package com.kh.service

import com.kh.util.SecretProperties
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage

class AlarmSender(private val sender: MailSender) {
    fun sendTrainReserved(receiver: String) {
        val secretProperties = SecretProperties()
        val message = SimpleMailMessage()
        message.setFrom(secretProperties.email.id)
        message.setTo(receiver)
        message.setSubject("[예약 봇] 예약 성공 알림")
        message.setText("""
            요청하신 빈자리 예약을 성공하였습니다.
            Korail 앱을 실행하여 예약내역을 확인하시기 바랍니다.
        """.trimIndent())
        sender.send(message)
    }
}