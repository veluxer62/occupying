package com.kh.occupying

import org.junit.Before
import org.junit.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
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

}

