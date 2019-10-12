package com.kh.api

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@WebFluxTest
@Import(value = [KakaoSkillHandler::class, Config::class])
class KakaoSkillApiTest {

    @Autowired
    private lateinit var webClient: WebTestClient

    lateinit var id: String
    lateinit var pw: String

    @BeforeEach
    fun setUp() {
        val resource = ClassPathResource("local.properties")
        val prop = Properties()
        prop.load(resource.inputStream)
        id = prop.getProperty("id")
        pw = prop.getProperty("pw")
    }

    @Test
    fun `test find trains`() {
        // Arrange
        val departureDate = LocalDate.now().plusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"))
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
                "name": "g0xxzoae15",
                "clientExtra": null,
                "params": {
                  "departure-date": "$departureDate",
                  "departure-time": "070000",
                  "departure-station": "서울",
                  "destination": "부산"
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
                  "destination": {
                    "origin": "부산",
                    "value": "부산",
                    "groupName": ""
                  }
                }
              }
            }
        """.trimIndent()

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

    @Test
    fun `test reserve train`() {
        // Arrange
        val departureDate = LocalDate.now().plusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"))
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
                        "train-no": "109",
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
        val listCardPath = "$.template.outputs[0].carousel"
        webClient.post()
                .uri("/api/kakao/reserve-train")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(body))
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.version").isEqualTo("2.0")
                .jsonPath("\$.template.outputs[0].simpleText.text").isNotEmpty
    }
}