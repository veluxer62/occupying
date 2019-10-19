package com.kh.service

import com.kh.api.Config
import com.kh.util.GmailReader
import com.kh.util.SecretProperties
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.mail.Message

@SpringBootTest(classes = [Config::class])
internal class AlarmSenderTest {

    @Autowired
    lateinit var alarmSender: AlarmSender

    lateinit var secret: SecretProperties

    @BeforeEach
    internal fun setUp() {
        secret = SecretProperties()
    }

    @Test
    fun `test sendSuccessMessage`() {
        // Arrange & Act
        alarmSender.sendSuccessMessage(secret.email.id)

        // Assert
        val messages = readMessages()
        val message = messages.find {
            it.subject.contains("예약 성공")
        }

        assertThat(messages.size).isGreaterThan(0)
        assertThat(message).isNotNull
        assertThat(message?.content).isNotNull
        assertThat(message?.content.toString()).contains("성공")
    }

    @Test
    fun `test sendFailMessage`() {
        // Arrange & Act
        alarmSender.sendFailMessage(secret.email.id)

        // Assert
        val messages = readMessages()
        val message = messages.find {
            it.subject.contains("예약 실패")
        }

        assertThat(messages.size).isGreaterThan(0)
        assertThat(message).isNotNull
        assertThat(message?.content).isNotNull
        assertThat(message?.content.toString()).contains("실패")
    }

    private fun readMessages(): Array<Message> {
        val mailReader = GmailReader(secret.email.id, secret.email.pw)
        return mailReader.getMessages()
    }

}