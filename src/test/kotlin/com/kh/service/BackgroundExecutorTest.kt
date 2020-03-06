package com.kh.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.kh.api.config.AppConfig
import com.kh.api.request.ReservationParams
import com.kh.api.request.SearchTrainParams
import com.kh.api.request.SkillPayload
import com.kh.deprecatedOccupying.Korail
import com.kh.util.RequestBodyCreator
import com.kh.util.SecretProperties
import com.kh.util.mapTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SpringBootTest(classes = [AppConfig::class])
internal class BackgroundExecutorTest {

    @Autowired
    private lateinit var backgroundExecutor: BackgroundExecutor

    @Autowired
    private lateinit var korail: Korail

    @MockBean
    private lateinit var alarmSender: AlarmSender

    private lateinit var requestBodyCreator: RequestBodyCreator

    private val secretProperties = SecretProperties()

    @BeforeEach
    internal fun setUp() {
        requestBodyCreator = RequestBodyCreator(korail)
    }

    @Test
    fun `should send success mail`() {
        // Arrange
        val body = requestBodyCreator.reservationRequest(
                departureDate = LocalDate.now()
                        .format(DateTimeFormatter.ofPattern("yyyyMMdd")),
                departureTime = "070000",
                departureStation = "서울",
                destinationStation = "부산",
                id = secretProperties.korail.id,
                pw = secretProperties.korail.pw,
                email = secretProperties.email.id
        )

        val skillPayload = jacksonObjectMapper()
                .readValue(body, SkillPayload::class.java)
        val clientExtra = skillPayload.action.clientExtra.orEmpty()
        val searchPayload = clientExtra
                .mapTo<SearchTrainParams>()
                .getSearchParams()
        val trainNo = clientExtra["train-no"].toString()
        val reservationPayload = skillPayload.action.params
                .mapTo<ReservationParams>()

        // Act
        backgroundExecutor.reserveTrain(searchPayload, trainNo, reservationPayload)

        // Assert
        BDDMockito.verify(alarmSender).sendSuccessMessage(secretProperties.email.id)
    }

    @Test
    fun `given wrong id and pw should send fail mail`() {
        // Arrange
        val body = requestBodyCreator.reservationRequest(
                departureDate = LocalDate.now()
                        .format(DateTimeFormatter.ofPattern("yyyyMMdd")),
                departureTime = "070000",
                departureStation = "서울",
                destinationStation = "부산",
                id = "test",
                pw = "test",
                email = secretProperties.email.id
        )

        val skillPayload = jacksonObjectMapper()
                .readValue(body, SkillPayload::class.java)
        val clientExtra = skillPayload.action.clientExtra.orEmpty()
        val searchPayload = clientExtra
                .mapTo<SearchTrainParams>()
                .getSearchParams()
        val trainNo = clientExtra["train-no"].toString()
        val reservationPayload = skillPayload.action.params
                .mapTo<ReservationParams>()

        // Act
        backgroundExecutor.reserveTrain(searchPayload, trainNo, reservationPayload)

        // Assert
        BDDMockito.verify(alarmSender).sendFailMessage(secretProperties.email.id)
    }
}