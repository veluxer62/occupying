package com.kh.occupying

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.springframework.core.io.ClassPathResource
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class KorailTest {

    lateinit var id: String
    lateinit var pw: String

    @Before
    fun setUp() {
        val resource = ClassPathResource("local.properties")
        val prop = Properties()
        prop.load(resource.inputStream)
        id = prop.getProperty("id")
        pw = prop.getProperty("pw")
    }

    @Test
    fun `given id and password login method will return result correctly`() {
        // Act
        val actual = Korail().login(id, pw)

        // Assert
        assertThat(actual).isNotNull
        assertThat(actual.resultCode).isEqualTo("SUCC")
        assertThat(actual.MobileCredencial).isNotEmpty()
        assertThat(actual.email).isNotEmpty()
        assertThat(actual.userName).isNotEmpty()
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
        val actual = Korail().search(
                departureAt,
                departureStation,
                destination
        )

        // Assert
        assertThat(actual).isNotNull
        assertThat(actual.resultCode).isEqualTo("SUCC")
        assertThat(actual.train).isNotNull
        assertThat(actual.train.items).isNotEmpty
    }

}

