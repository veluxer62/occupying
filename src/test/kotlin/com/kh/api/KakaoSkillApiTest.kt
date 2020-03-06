package com.kh.api

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.kh.api.config.AppConfig
import com.kh.api.request.ReservationParams
import com.kh.api.request.SearchTrainParams
import com.kh.api.request.SkillPayload
import com.kh.deprecatedOccupying.Korail
import com.kh.service.BackgroundExecutor
import com.kh.util.RequestBodyCreator
import com.kh.util.SecretProperties
import com.kh.util.mapTo
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
        val basicCardPath = "$.template.outputs[0].basicCard"
        webClient.post()
                .uri("/api/kakao/find-trains")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(body))
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.version").isEqualTo("2.0")
                .jsonPath("$basicCardPath.title").value<String> {
                    assertThat(it).isEqualTo("열차 조회를 실패")
                }
                .jsonPath("$basicCardPath.description").value<String> {
                    assertThat(it).contains(expected)
                }
                .jsonPath("$basicCardPath.buttons[0].label").value<String> {
                    assertThat(it).isEqualTo("다시 조회")
                }
                .jsonPath("$basicCardPath.buttons[0].action").value<String> {
                    assertThat(it).isEqualTo("block")
                }
                .jsonPath("$basicCardPath.buttons[0].messageText").value<String> {
                    assertThat(it).isEqualTo("열차조회")
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
        val basicCardPath = "$.template.outputs[0].basicCard"
        webClient.post()
                .uri("/api/kakao/find-trains")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(body))
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.version").isEqualTo("2.0")
                .jsonPath("$basicCardPath.title").value<String> {
                    assertThat(it).isEqualTo("열차 조회를 실패")
                }
                .jsonPath("$basicCardPath.description").value<String> {
                    assertThat(it).contains("열차 조회를 다시 해주시기 바랍니다.")
                }
                .jsonPath("$basicCardPath.buttons[0].label").value<String> {
                    assertThat(it).isEqualTo("다시 조회")
                }
                .jsonPath("$basicCardPath.buttons[0].action").value<String> {
                    assertThat(it).isEqualTo("block")
                }
                .jsonPath("$basicCardPath.buttons[0].messageText").value<String> {
                    assertThat(it).isEqualTo("열차조회")
                }
    }

    @Test
    fun `test reserve train`() {
        // Arrange
        val departureTime = "070000"
        val departureStation = "서울"
        val destinationStation = "부산"
        val body = requestBodyCreator.reservationRequest(
                departureDate = departureDate,
                departureTime = departureTime,
                departureStation = departureStation,
                destinationStation = destinationStation,
                id = id,
                pw = pw,
                email = email
        )

        // Act & Assert
        val basicCardPath = "$.template.outputs[0].basicCard"
        webClient.post()
                .uri("/api/kakao/reserve-train")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(body))
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.version").isEqualTo("2.0")
                .jsonPath("$basicCardPath.title").value<String> {
                    assertThat(it).isEqualTo("예약 신청 완료")
                }
                .jsonPath("$basicCardPath.description").isNotEmpty
                .jsonPath("$basicCardPath.buttons[0].label").value<String> {
                    assertThat(it).isEqualTo("다시 예약신청")
                }
                .jsonPath("$basicCardPath.buttons[0].action").value<String> {
                    assertThat(it).isEqualTo("block")
                }
                .jsonPath("$basicCardPath.buttons[0].messageText").value<String> {
                    assertThat(it).isEqualTo("다시예약신청")
                }
                .jsonPath("$basicCardPath.buttons[0].extra.departure-date").value<String> {
                    assertThat(it).isEqualTo(departureDate)
                }
                .jsonPath("$basicCardPath.buttons[0].extra.departure-time").value<String> {
                    assertThat(it).isEqualTo(departureTime)
                }
                .jsonPath("$basicCardPath.buttons[0].extra.train-no").isNotEmpty
                .jsonPath("$basicCardPath.buttons[0].extra.departure-station").value<String> {
                    assertThat(it).isEqualTo(departureStation)
                }
                .jsonPath("$basicCardPath.buttons[0].extra.destination-station").value<String> {
                    assertThat(it).isEqualTo(destinationStation)
                }
                .jsonPath("$basicCardPath.buttons[0].extra.id").value<String> {
                    assertThat(it).isEqualTo(id)
                }
                .jsonPath("$basicCardPath.buttons[0].extra.pw").value<String> {
                    assertThat(it).isEqualTo(pw)
                }
                .jsonPath("$basicCardPath.buttons[0].extra.email").value<String> {
                    assertThat(it).isEqualTo(email)
                }

        val payload = jacksonObjectMapper().readValue(body, SkillPayload::class.java)
        val clientExtra = payload.action.clientExtra.orEmpty()
        val searchPayload = clientExtra.mapTo<SearchTrainParams>()
                .getSearchParams()
        val trainNo = clientExtra["train-no"].toString()
        val reservationPayload = payload.action.params.mapTo<ReservationParams>()
        BDDMockito.verify(backgroundExecutor).reserveTrain(
                searchPayload, trainNo, reservationPayload)
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

    @Test
    fun `test retry reserve train`() {
        // Arrange
        val departureTime = "070000"
        val departureStation = "서울"
        val destinationStation = "부산"
        val body = requestBodyCreator.retryReservationRequest(
                departureDate = departureDate,
                departureTime = departureTime,
                departureStation = departureStation,
                destinationStation = destinationStation,
                id = id,
                pw = pw,
                email = email
        )

        // Act & Assert
        val basicCardPath = "$.template.outputs[0].basicCard"
        webClient.post()
                .uri("/api/kakao/retry-train-reservation")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(body))
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.version").isEqualTo("2.0")
                .jsonPath("$basicCardPath.title").value<String> {
                    assertThat(it).isEqualTo("예약 신청 완료")
                }
                .jsonPath("$basicCardPath.description").isNotEmpty
                .jsonPath("$basicCardPath.buttons[0].label").value<String> {
                    assertThat(it).isEqualTo("다시 예약신청")
                }
                .jsonPath("$basicCardPath.buttons[0].action").value<String> {
                    assertThat(it).isEqualTo("block")
                }
                .jsonPath("$basicCardPath.buttons[0].messageText").value<String> {
                    assertThat(it).isEqualTo("다시예약신청")
                }
                .jsonPath("$basicCardPath.buttons[0].extra.departure-date").value<String> {
                    assertThat(it).isEqualTo(departureDate)
                }
                .jsonPath("$basicCardPath.buttons[0].extra.departure-time").value<String> {
                    assertThat(it).isEqualTo(departureTime)
                }
                .jsonPath("$basicCardPath.buttons[0].extra.train-no").isNotEmpty
                .jsonPath("$basicCardPath.buttons[0].extra.departure-station").value<String> {
                    assertThat(it).isEqualTo(departureStation)
                }
                .jsonPath("$basicCardPath.buttons[0].extra.destination-station").value<String> {
                    assertThat(it).isEqualTo(destinationStation)
                }
                .jsonPath("$basicCardPath.buttons[0].extra.id").value<String> {
                    assertThat(it).isEqualTo(id)
                }
                .jsonPath("$basicCardPath.buttons[0].extra.pw").value<String> {
                    assertThat(it).isEqualTo(pw)
                }
                .jsonPath("$basicCardPath.buttons[0].extra.email").value<String> {
                    assertThat(it).isEqualTo(email)
                }

        val payload = jacksonObjectMapper().readValue(body, SkillPayload::class.java)
        val clientExtra = payload.action.clientExtra.orEmpty()
        val searchPayload = clientExtra.mapTo<SearchTrainParams>()
                .getSearchParams()
        val trainNo = clientExtra["train-no"].toString()
        val reservationPayload = clientExtra.mapTo<ReservationParams>()
        BDDMockito.verify(backgroundExecutor).reserveTrain(
                searchPayload, trainNo, reservationPayload)
    }

}