package com.kh.occupying

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.springframework.core.io.ClassPathResource
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

}

