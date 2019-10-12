package com.kh.occupying

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class KorailPropertiesTest {

    @Test
    fun `test properties`() {
        // Arrange & Act
        val sut = KorailProperties()

        // Assert
        Assertions.assertThat(sut.host).isNotEmpty()
        Assertions.assertThat(sut.contextPath).isNotEmpty()
    }
}