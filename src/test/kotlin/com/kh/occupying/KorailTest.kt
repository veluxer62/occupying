package com.kh.occupying

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class KorailTest {

    @Test
    fun `given id and password login method will return result correctly`() {
        // Arrange
        val id = "korail-id"
        val pw = "korail-pw"

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

