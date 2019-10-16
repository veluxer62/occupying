package com.kh.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SecretPropertiesTest {

    @Test
    fun `test properties`() {
        // Arrange & Act
        val sut = SecretProperties()

        // Assert
        assertThat(sut.korail.id).isNotEmpty()
        assertThat(sut.korail.pw).isNotEmpty()
        assertThat(sut.email.id).isNotEmpty()
        assertThat(sut.email.pw).isNotEmpty()
    }

}