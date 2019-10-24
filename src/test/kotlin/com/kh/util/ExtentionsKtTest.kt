package com.kh.util

import com.fasterxml.jackson.annotation.JsonProperty
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.*
import java.util.stream.Stream

class ExtentionsTest {

    data class SampleClass(
            val stringField: String,
            val intField: Int,
            val longField: Long,
            val booleanField: Boolean,
            val floatField: Float,
            val doubleField: Double,
            val mapField: Map<String, Any>,
            val listField: List<Map<String, Any>>
    )

    data class SampleWithJsonPropertyClass(
            @JsonProperty("string-field")
            val stringField: String,
            @JsonProperty("int-field")
            val intField: Int,
            @JsonProperty("long-field")
            val longField: Long,
            @JsonProperty("boolean-field")
            val booleanField: Boolean,
            @JsonProperty("float-field")
            val floatField: Float,
            @JsonProperty("double-field")
            val doubleField: Double,
            @JsonProperty("map-field")
            val mapField: Map<String, Any>,
            @JsonProperty("list-field")
            val listField: List<Map<String, Any>>
    )

    companion object {
        private val stringField = UUID.randomUUID().toString()
        private val intField = Random().nextInt()
        private val longField = Random().nextLong()
        private val booleanField = Random().nextBoolean()
        private val floatField = Random().nextFloat()
        private val doubleField = Random().nextDouble()
        private val mapField = mapOf(
                "stringField" to stringField,
                "intField" to intField
        )
        private val mapField2 = mapOf(
                "longField" to longField,
                "booleanField" to booleanField
        )
        private val listField = listOf(
                mapField,
                mapField2
        )

        @JvmStatic
        fun init(): Stream<Arguments> {
            val map = mapOf(
                    "stringField" to stringField,
                    "intField" to intField,
                    "longField" to longField,
                    "booleanField" to booleanField,
                    "floatField" to floatField,
                    "doubleField" to doubleField,
                    "mapField" to mapField,
                    "listField" to listField
            )
            val sample = SampleClass(
                    stringField = stringField,
                    intField = intField,
                    longField = longField,
                    booleanField = booleanField,
                    floatField = floatField,
                    doubleField = doubleField,
                    mapField = mapField,
                    listField = listField
            )
            return Stream.of(
                    Arguments.of(map, sample)
            )
        }

        @JvmStatic
        fun initWithJsonPropertyClass(): Stream<Arguments> {
            val map = mapOf(
                    "string-field" to stringField,
                    "int-field" to intField,
                    "long-field" to longField,
                    "boolean-field" to booleanField,
                    "float-field" to floatField,
                    "double-field" to doubleField,
                    "map-field" to mapField,
                    "list-field" to listField
            )
            val sample = SampleWithJsonPropertyClass(
                    stringField = stringField,
                    intField = intField,
                    longField = longField,
                    booleanField = booleanField,
                    floatField = floatField,
                    doubleField = doubleField,
                    mapField = mapField,
                    listField = listField
            )
            return Stream.of(
                    Arguments.of(map, sample)
            )
        }

        @JvmStatic
        fun initEmail(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of("test", false),
                    Arguments.of("john.doe@mail.com", true),
                    Arguments.of("test@gmail.com", true),
                    Arguments.of("xxxx@yyyy.zzz", false),
                    Arguments.of("asdf@asdf.asdf", false),
                    Arguments.of("john.doe@somewhere.com", true),
                    Arguments.of("joe@server.com", true),
                    Arguments.of("n@naver.com", true)
            )
        }
    }

    @ParameterizedTest
    @MethodSource("init")
    fun `given map mapTo method will return class correctly`(
            sut: Map<String, Any>,
            expected: SampleClass
    ) {
        // Arrange & Act
        val actual = sut.mapTo<SampleClass>()

        // Assert
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("initWithJsonPropertyClass")
    fun `given map mapTo method will return class with JsonProperty correctly`(
            sut: Map<String, Any>,
            expected: SampleWithJsonPropertyClass
    ) {
        // Arrange & Act
        val actual = sut.mapTo<SampleWithJsonPropertyClass>()

        // Assert
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("initEmail")
    fun `test isEmail method`(email: String, expected: Boolean) {
        // Arrange & Act
        val actual = email.isEmail()

        // Assert
        assertThat(actual).isEqualTo(expected)
    }
}