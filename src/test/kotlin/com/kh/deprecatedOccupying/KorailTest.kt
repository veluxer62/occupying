package com.kh.deprecatedOccupying

import com.kh.deprecatedOccupying.domain.Station
import com.kh.deprecatedOccupying.dto.param.SearchParams
import com.kh.deprecatedOccupying.dto.response.*
import com.kh.util.SecretProperties
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDate
import java.util.*

class KorailTest {

    private lateinit var id: String
    private lateinit var pw: String
    private lateinit var client: WebClientWrapper
    private lateinit var sut: Korail

    @BeforeEach
    fun setUp() {
        val secretProperties = SecretProperties()
        id = secretProperties.korail.id
        pw = secretProperties.korail.pw
        client = WebClientWrapper(KorailProperties())
        sut = Korail(client)
    }

    @Test
    fun `given id and password login method will return result correctly`() {
        // Act
        val actual = sut.login(id, pw)

        // Assert
        StepVerifier
                .create(actual)
                .expectNextMatches {
                    val response = it as LoginResponse
                    response.resultCode == ResultCode.SUCC &&
                            response.key.isNotEmpty() &&
                            (response.cookie ?: listOf()).isNotEmpty()
                }
                .verifyComplete()
    }

    @Test
    fun `given wrong id and password login method will return fail response correctly`() {
        // Arrange
        val id = UUID.randomUUID().toString()
        val pw = UUID.randomUUID().toString()

        // Act
        val actual = sut.login(id, pw)

        // Assert
        StepVerifier
                .create(actual)
                .expectNextMatches {
                    val result = it as FailResponse
                    result.responseCode.isNotEmpty() &&
                            result.responseMessage.isNotEmpty() &&
                            (result.resultCode == ResultCode.SUCC ||
                                    result.resultCode == ResultCode.FAIL)
                }
                .verifyComplete()
    }

    @Test
    fun `given param search method will return result correctly`() {
        // Arrange & Act
        val actual = searchTrain()

        // Assert
        StepVerifier
                .create(actual)
                .expectNextMatches {
                    val result = it as SearchResponse
                    result.resultCode == ResultCode.SUCC &&
                            result.train.items.isNotEmpty()
                }
                .verifyComplete()
    }

    @Test
    fun `given train reserve method will return result correctly`() {
        // Arrange
        val loginResult = sut.login(id, pw)

        // Actual
        val searchResult = searchTrain()

        // Act
        val actual = Mono
                .zip(loginResult, searchResult)
                .flatMap {
                    val train = (it.t2 as SearchResponse)
                            .train.items
                            .map { item -> item.toDomain() }
                            .first {train ->
                                train.hasSeat()
                            }
                    val login = (it.t1 as LoginResponse).toDomain()

                    sut.reserve(login, train)
                }

        // Assert
        StepVerifier
                .create(actual)
                .expectNextMatches {
                    if (it is ReservationResponse) {
                        assertThat(it.jrnyInfos.jrnyInfo).isNotEmpty
                        assertThat(it.psgInfos.psgInfo).isNotEmpty
                    } else {
                        assertThat(it.responseCode).isEqualTo("WRR800029")
                        assertThat(it.responseMessage).contains("동일한")
                    }

                    true
                }
                .verifyComplete()
    }

    private fun searchTrain(): Mono<CommonResponse> {
        val params = SearchParams(
                departureDatetime = LocalDate.now()
                        .plusDays(1)
                        .atTime(7, 0, 0),
                departureStation = Station.서울,
                destinationStation = Station.부산
        )

        return sut.search(params)
    }

    @Test
    fun `findAvailableTrain method will return train correctly`() {
        // TODO https://github.com/square/okhttp/tree/master/mockwebserver
    }
}

