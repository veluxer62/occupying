package com.kh.service

import com.kh.util.SecretProperties
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage

class AlarmSender(private val sender: MailSender) {
    private val secretProperties = SecretProperties()

    fun sendSuccessMessage(receiver: String) {
        val subject = "[예약 봇] 예약 성공 알림"
        val content = """
            요청하신 빈자리 예약을 성공하였습니다.
            Korail 앱을 실행하여 예약내역을 확인하시기 바랍니다.
        """.trimIndent()
        val message = generateMessage(receiver, subject, content)
        sender.send(message)
    }

    fun sendFailMessage(receiver: String) {
        val subject = "[예약 봇] 예약 실패 알림"
        val content = """
            요청하신 빈자리 예약을 실패하였습니다.
            예약 프로세스를 다시 실행해 주시기 바랍니다.
        """.trimIndent()
        val message = generateMessage(receiver, subject, content)
        sender.send(message)
    }

    private fun generateMessage(receiver: String,
                                subject: String,
                                content: String): SimpleMailMessage {
        val message = SimpleMailMessage()
        message.setFrom(secretProperties.email.id)
        message.setTo(receiver)
        message.setSubject(subject)
        message.setText(content)
        return message
    }
}