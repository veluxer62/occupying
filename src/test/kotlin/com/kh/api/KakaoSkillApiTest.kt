package com.kh.api

import com.kh.api.response.SkillResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters

@RunWith(SpringRunner::class)
@WebFluxTest
@Import(value = [KakaoSkillHandler::class, Config::class])
class KakaoSkillApiTest {

    @Autowired
    private lateinit var webClient: WebTestClient

    @Test
    fun `test find trains`() {
        // Arrange
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
                  "departure-date": "20191010",
                  "departure-time": "070000",
                  "departure-station": "서울",
                  "destination": "부산"
                },
                "id": "krcu052b90c6angense4fxgy",
                "detailParams": {
                  "departure-date": {
                    "origin": "20191010",
                    "value": "20191010",
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

        // Act
        val actual = webClient.post()
                .uri("/api/kakao/find-trains")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(body))
                .exchange()
                .expectStatus().isOk
                .expectBody(SkillResponse::class.java)
                .returnResult()
                .responseBody

        // Assert
        val outputs = actual?.template?.outputs
        val listCard = outputs?.first()?.listCard
        assertThat(actual).isNotNull
        assertThat(actual?.version).isEqualTo("2.0")
        assertThat(outputs).isNotEmpty
        assertThat(listCard?.items?.size).isLessThanOrEqualTo(5)
        assertThat(listCard?.buttons?.size).isLessThanOrEqualTo(2)
    }
}