package com.kh.service

import com.kh.api.Config
import com.kh.util.GmailReader
import com.kh.util.SecretProperties
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [Config::class])
internal class AlarmSenderTest {

    @Autowired
    lateinit var alarmSender: AlarmSender

    @Test
    fun `test sendTrainReserved`() {
        // Arrange
        val secret = SecretProperties()
        val receiver = secret.email.id

        // Act
        alarmSender.sendTrainReserved(receiver)

        // Assert
        val mailReader = GmailReader(secret.email.id, secret.email.pw)
        val messages = mailReader.getMessages()
        val message = messages.find {
            it.subject.contains("예약 성공")
        }

        assertThat(messages.size).isGreaterThan(0)
        assertThat(message).isNotNull
        assertThat(message?.content).isNotNull
    }

}