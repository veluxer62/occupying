package com.kh.api

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.kh.api.request.SearchTrainParams
import com.kh.api.request.SkillPayload
import com.kh.occupying.Korail
import com.kh.occupying.domain.Train
import com.kh.occupying.dto.response.SearchResponse
import com.kh.util.SecretProperties
import com.kh.util.mapTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.platform.commons.annotation.Testable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@WebFluxTest
@Import(value = [KakaoSkillHandler::class, Config::class])
class KakaoSkillApiTest {

    @Autowired
    private lateinit var webClient: WebTestClient

    @Autowired
    private lateinit var korail: Korail

    private lateinit var id: String
    private lateinit var pw: String
    private lateinit var departureDate: String

    @BeforeEach
    fun setUp() {
        val secretProperties = SecretProperties()
        id = secretProperties.korail.id
        pw = secretProperties.korail.pw

        departureDate = LocalDate.now().plusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"))
    }

    @Test
    fun `test find trains`() {
        // Arrange
        val body = findTrainBody()

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

    private fun findTrainBody(): String {
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
                      "departure-time": "070000",
                      "departure-station": "서울",
                      "destination-station": "부산"
                    },
                    "id": "krcu052b90c6angense4fxgy",
                    "detailParams": {
                      "departure-date": {
                        "origin": "$departureDate",
                        "value": "$departureDate",
                        "groupName": ""
                      },
                      "departure-time": {
                        "origin": "070000",
                        "value": "070000",
                        "groupName": ""
                      },
                      "departure-station": {
                        "origin": "서울",
                        "value": "서울",
                        "groupName": ""
                      },
                      "destination-station": {
                        "origin": "부산",
                        "value": "부산",
                        "groupName": ""
                      }
                    }
                  }
                }
            """.trimIndent()
    }

    @Test
    @Disabled("""
        매진인 경우 테스트 성공시까지 시간이 오래 소요되므로 메뉴얼로만 테스트를 실행한다.
    """)
    fun `test reserve train`() {
        // Arrange
        val trainNo = getTrainNo()
        val body = """
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
                        "pw": "$pw"
                    },
                    "id": "5d9dc807ffa7480001dace7e",
                    "detailParams": {
                        "id": {
                            "origin": "$id",
                            "value": "$id",
                            "groupName": ""
                        },
                        "pw": {
                            "origin": "$pw",
                            "value": "$pw",
                            "groupName": ""
                        }
                    }
                }
            }
        """.trimIndent()

        // Act & Assert
        webClient = webClient.mutate()
                .responseTimeout(Duration.ofSeconds(30 * 60))
                .build()
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
    }

    private fun getTrainNo(): String {
        val request = jacksonObjectMapper()
                .readValue(findTrainBody(), SkillPayload::class.java)
        val payload = request.action.params
                .mapTo<SearchTrainParams>()
                .getSearchParams()
        return korail.search(payload).map {
            (it as SearchResponse).train.items
                    .map { response -> response.toDomain() }
                    .first()
                    .no
        }.block()!!
    }
}