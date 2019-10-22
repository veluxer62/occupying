package com.kh.api

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.kh.api.config.AppConfig
import com.kh.api.request.SearchTrainParams
import com.kh.api.request.SkillPayload
import com.kh.occupying.Korail
import com.kh.occupying.dto.response.SearchResponse
import com.kh.service.BackgroundExecutor
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
    }

    @Test
    fun `test find trains`() {
        // Arrange
        val body = findTrainRequest(
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
    }

    @ParameterizedTest
    @MethodSource("initFindTrainsData")
    fun `given wrong request find trains will return body correctly`(
            departureDate: String, departureTime: String,
            departureStation: String, destinationStation: String,
            expected: String
    ) {
        // Arrange
        val body = findTrainRequest(
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
        val body = findTrainRequest(
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
    }

    @Test
    fun `test reserve train`() {
        // Arrange
        val body = reservationRequest(id, pw, email)

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

    private fun findTrainRequest(
            departureDate: String,
            departureTime: String,
            departureStation: String,
            destinationStation: String
    ): String {
        return """
                {
                  "intent": {
                    "id": "d7coyklcoly0q5lhjh6zdjzd",
                    "name": "블록 이름"
                  },
                  "userRequest": {
                    "timezone": "Asia/Seoul",
                    "params": {
                      "ignoreMe": "true"
                    },
                    "block": {
                      "id": "d7coyklcoly0q5lhjh6zdjzd",
                      "name": "블록 이름"
                    },
                    "utterance": "발화 내용",
                    "lang": null,
                    "user": {
                      "id": "515520",
                      "type": "accountId",
                      "properties": {}
                    }
                  },
                  "bot": {
                    "id": "5d97076fb617ea00012af4e0",
                    "name": "봇 이름"
                  },
                  "action": {
                    "name": "g0xxzoae15",
                    "clientExtra": null,
                    "params": {
                      "departure-date": "$departureDate",
                      "departure-time": "$departureTime",
                      "departure-station": "$departureStation",
                      "destination-station": "$destinationStation"
                    },
                    "id": "krcu052b90c6angense4fxgy"
                  }
                }
            """.trimIndent()
    }

    private fun reservationRequest(id: String, pw: String, email: String): String {
        val trainNo = getTrainNo()
        return """
                {
                    "intent": {
                    "id": "d7coyklcoly0q5lhjh6zdjzd",
                    "name": "블록 이름"
                  },
                  "userRequest": {
                    "timezone": "Asia/Seoul",
                    "params": {
                      "ignoreMe": "true"
                    },
                    "block": {
                      "id": "d7coyklcoly0q5lhjh6zdjzd",
                      "name": "블록 이름"
                    },
                    "utterance": "발화 내용",
                    "lang": null,
                    "user": {
                      "id": "515520",
                      "type": "accountId",
                      "properties": {}
                    }
                  },
                  "bot": {
                    "id": "5d97076fb617ea00012af4e0",
                    "name": "봇 이름"
                  },
                    "action": {
                        "name": "예약 API",
                        "clientExtra": {
                            "departure-time": "070000",
                            "departure-date": "$departureDate",
                            "train-no": "$trainNo",
                            "destination-station": "부산",
                            "departure-station": "서울"
                        },
                        "params": {
                            "id": "$id",
                            "pw": "$pw",
                            "email": "$email"
                        },
                        "id": "5d9dc807ffa7480001dace7e"
                    }
                }
            """.trimIndent()
    }

    private fun getTrainNo(): String {
        val request = jacksonObjectMapper()
                .readValue(findTrainRequest(
                        departureDate = departureDate,
                        departureTime = "070000",
                        departureStation = "서울",
                        destinationStation = "부산"
                ), SkillPayload::class.java)
        val payload = request.action.params
                .mapTo<SearchTrainParams>()
                .getSearchParams()
        return korail.search(payload).map {
            (it as SearchResponse).train.items
                    .map { response -> response.toDomain() }
                    .first { train -> train.hasSeat() }
                    .no
        }.block()!!
    }
}