package com.kh.api

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.kh.api.config.AppConfig
import com.kh.api.request.SkillPayload
import com.kh.occupying.Korail
import com.kh.service.BackgroundExecutor
import com.kh.util.RequestBodyCreator
import com.kh.util.SecretProperties
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.stream.Stream

@WebFluxTest
@Import(value = [KakaoSkillHandler::class, AppConfig::class])
class KakaoSkillApiTest {

    @Autowired
    private lateinit var webClient: WebTestClient

    @Autowired
    private lateinit var korail: Korail

    @MockBean
    private lateinit var backgroundExecutor: BackgroundExecutor

    private lateinit var requestBodyCreator: RequestBodyCreator

    private lateinit var id: String
    private lateinit var pw: String
    private lateinit var departureDate: String
    private lateinit var email: String

    companion object {
        @JvmStatic
        fun initFindTrainsData(): Stream<Arguments> {
            val departureDate = LocalDate.now().plusDays(1)
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd"))
            return Stream.of(
                    Arguments.of(UUID.randomUUID().toString(), "070000", "서울", "부산", "출발일"),
                    Arguments.of(departureDate, UUID.randomUUID().toString(), "서울", "부산", "출발시간"),
                    Arguments.of(departureDate, "070000", UUID.randomUUID().toString(), "부산", "출발역"),
                    Arguments.of(departureDate, "070000", "서울", UUID.randomUUID().toString(), "도착역")
            )
        }
    }

    @BeforeEach
    fun setUp() {
        val secretProperties = SecretProperties()
        id = secretProperties.korail.id
        pw = secretProperties.korail.pw
        email = secretProperties.email.id

        departureDate = LocalDate.now().plusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"))

        requestBodyCreator = RequestBodyCreator(korail)
    }

    @Test
    fun `test find trains`() {
        // Arrange
        val body = requestBodyCreator.findTrainRequest(
                departureDate = departureDate,
                departureTime = "070000",
                departureStation = "서울",
                destinationStation = "부산"
        )

        // Act & Assert
        val listCardPath = "$.template.outputs[0].carousel"
        webClient.post()
                .uri("/api/kakao/find-trains")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(body))
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.version").isEqualTo("2.0")
                .jsonPath("$listCardPath.type").isEqualTo("basicCard")
                .jsonPath("$listCardPath.items").isArray
                .jsonPath("$listCardPath.items[?(@.title != '')]").isNotEmpty
                .jsonPath("$listCardPath.items[?(@.description != '')]").isNotEmpty
                .jsonPath("$listCardPath.items[?(@.thumbnail.imageUrl != '')]").isNotEmpty
                .jsonPath("$listCardPath.items[?(@.buttons[0].label == '예약하기')]").isNotEmpty
                .jsonPath("$listCardPath.items[?(@.buttons[0].action == 'block')]").isNotEmpty
                .jsonPath("$listCardPath.items[?(@.buttons[0].blockId != '')]").isNotEmpty
                .jsonPath("$listCardPath.items[?(@.buttons[0].extra != null)]").isNotEmpty
                .jsonPath("$listCardPath.items[?(@.buttons[0].extra.departure-date != '')]").isNotEmpty
                .jsonPath("$listCardPath.items[?(@.buttons[0].extra.departure-time != '')]").isNotEmpty
                .jsonPath("$listCardPath.items[?(@.buttons[0].extra.train-no != '')]").isNotEmpty
                .jsonPath("$listCardPath.items[?(@.buttons[0].extra.departure-station != '')]").isNotEmpty
                .jsonPath("$listCardPath.items[?(@.buttons[0].extra.destination-station != '')]").isNotEmpty
    }

    @ParameterizedTest
    @MethodSource("initFindTrainsData")
    fun `given wrong request find trains will return body correctly`(
            departureDate: String, departureTime: String,
            departureStation: String, destinationStation: String,
            expected: String
    ) {
        // Arrange
        val body = requestBodyCreator.findTrainRequest(
                departureDate = departureDate,
                departureTime = departureTime,
                departureStation = departureStation,
                destinationStation = destinationStation
        )

        // Act & Assert
        webClient.post()
                .uri("/api/kakao/find-trains")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(body))
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.version").isEqualTo("2.0")
                .jsonPath("$.template.outputs[0].simpleText.text").isNotEmpty
                .jsonPath("$.template.outputs[0].simpleText.text").value<String> {
                    assertThat(it).contains(expected)
                }
    }

    @Test
    fun `test find trains response failed`() {
        // Arrange
        val body = requestBodyCreator.findTrainRequest(
                departureDate = "20180101",
                departureTime = "070000",
                departureStation = "서울",
                destinationStation = "부산"
        )

        // Act & Assert
        webClient.post()
                .uri("/api/kakao/find-trains")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(body))
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.version").isEqualTo("2.0")
                .jsonPath("$.template.outputs[0].simpleText.text").isNotEmpty
                .jsonPath("$.template.outputs[0].simpleText.text").value<String> {
                    assertThat(it).contains("열차 조회를 다시 해주시기 바랍니다.")
                }
    }

    @Test
    fun `test reserve train`() {
        // Arrange
        val body = requestBodyCreator.reservationRequest(
                departureDate = departureDate,
                departureTime = "070000",
                departureStation = "서울",
                destinationStation = "부산",
                id = id,
                pw = pw,
                email = email
        )

        // Act & Assert
        webClient.post()
                .uri("/api/kakao/reserve-train")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(body))
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.version").isEqualTo("2.0")
                .jsonPath("$.template.outputs[0].simpleText.text").isNotEmpty

        val payload = jacksonObjectMapper().readValue(body, SkillPayload::class.java)
        BDDMockito.verify(backgroundExecutor).reserveTrain(payload)
    }

    @Test
    fun `given wrong email reserve train will return response correctly`() {
        // Arrange
        val body = requestBodyCreator.reservationRequest(
                departureDate = departureDate,
                departureTime = "070000",
                departureStation = "서울",
                destinationStation = "부산",
                id = id,
                pw = pw,
                email = "test"
        )

        // Act & Assert
        webClient.post()
                .uri("/api/kakao/reserve-train")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(body))
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.version").isEqualTo("2.0")
                .jsonPath("$.template.outputs[0].simpleText.text").isNotEmpty
                .jsonPath("$.template.outputs[0].simpleText.text").value<String> {
                    assertThat(it).contains("예약 신청을 다시 해주시기 바랍니다.")
                }
    }

}