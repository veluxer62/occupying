package com.kh.occupying

import com.kh.occupying.domain.Login
import com.kh.occupying.domain.Train
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

    @Before
    fun setUp() {
        val resource = ClassPathResource("local.properties")
        val prop = Properties()
        prop.load(resource.inputStream)
        id = prop.getProperty("id")
        pw = prop.getProperty("pw")
        client = WebClientWrapper(KorailProperties())
    }

    @Test
    fun `given id and password login method will return result correctly`() {
        // Act
        val actual = Korail(client).login(id, pw)

        // Assert
        StepVerifier
                .create(actual)
                .expectNextMatches {
                    it.resultCode == "SUCC" &&
                            it.MobileCredencial.isNotEmpty() &&
                            it.email.isNotEmpty() &&
                            it.userName.isNotEmpty()
                }
                .verifyComplete()
    }

    @Test
    fun `given param search method will return result correctly`() {
        // Arrange
        val departureDate = LocalDate.now()
                .plusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        // 2019091507
        val departureAt = departureDate + "07"
        val departureStation = "서울"
        val destination = "부산"

        // Act
        val actual = Korail(client).search(
                departureAt,
                departureStation,
                destination
        )

        // Assert
        StepVerifier
                .create(actual)
                .expectNextMatches {
                    it.resultCode == "SUCC" &&
                            it.train.items.isNotEmpty()
                }
                .verifyComplete()
    }

    @Test
    fun `given train reserve method will return result correctly`() {
        // Arrange
        val sut = Korail(client)
        val loginResult = sut.login(id, pw)

        val departureDate = LocalDate.now()
                .plusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        // 2019091507
        val departureAt = departureDate + "07"
        val departureStation = "서울"
        val destination = "부산"

        // Act
        val searchResult = Korail(client).search(
                departureAt,
                departureStation,
                destination
        )

        // Act
        val actual = Mono
                .zip(loginResult, searchResult)
                .flatMap {
                    val canReserveTrain = it.t2.train.items
                            .first { x -> x.canReservation == "Y" }
                    val train = Train.fromDto(canReserveTrain)
                    val login = Login(it.t1)

                    sut.reserve(login, train)
                }

        // Assert
        StepVerifier
                .create(actual)
                .expectNextMatches {
                    it.resultCode == "SUCC" || it.resultCode == "FAIL"
                }
                .verifyComplete()
    }

}

