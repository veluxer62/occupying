package com.kh.occupying

import com.kh.occupying.domain.Login
import com.kh.occupying.domain.Train
import com.kh.occupying.dto.response.*
import org.junit.Before
import org.junit.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@SpringBootTest
class KorailTest {

    lateinit var id: String
    lateinit var pw: String
    lateinit var client: WebClientWrapper
    lateinit var sut: Korail

    @Before
    fun setUp() {
        val resource = ClassPathResource("local.properties")
        val prop = Properties()
        prop.load(resource.inputStream)
        id = prop.getProperty("id")
        pw = prop.getProperty("pw")
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
                    val canReserveTrain = (it.t2 as SearchResponse)
                            .train.items
                            .first { x -> x.canReservation == "Y" }
                    val train = Train.fromDto(canReserveTrain)
                    val login = Login.fromDto(it.t1 as LoginResponse)

                    sut.reserve(login, train)
                }

        // Assert
        StepVerifier
                .create(actual)
                .expectNextMatches {
                    (it.resultCode == ResultCode.SUCC ||
                            it.resultCode == ResultCode.FAIL) &&
                            it.responseCode.isNotEmpty() &&
                            it.responseMessage.isNotEmpty()
                }
                .verifyComplete()
    }

    private fun searchTrain(): Mono<CommonResponse> {
        val departureDate = LocalDate.now()
                .plusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        // 2019091507
        val departureAt = departureDate + "07"
        val departureStation = "서울"
        val destination = "부산"

        return sut.search(
                departureAt,
                departureStation,
                destination
        )
    }

}

