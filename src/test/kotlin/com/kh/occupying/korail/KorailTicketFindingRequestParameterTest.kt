package com.kh.occupying.korail

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import java.time.LocalDate
import java.time.LocalTime
import java.util.stream.Stream

internal class KorailTicketFindingRequestParameterTest {

    @ParameterizedTest
    @ArgumentsSource(KorailTicketFindingRequestParameterArgumentsProvider::class)
    fun `getQueryParams method will return MultiValueMap if given data correctly`(
            sut: KorailTicketFindingRequestParameter,
            expected: MultiValueMap<String, String>
    ) {
        val actual = sut.getQueryParams()

        assertThat(actual).isEqualTo(expected)
    }

    internal class KorailTicketFindingRequestParameterArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<Arguments> {
            return Stream.of(
                    Arguments {
                        val sut = KorailTicketFindingRequestParameter(
                                departureDate = LocalDate.of(2000, 1, 1),
                                departureTime = LocalTime.of(1, 1, 1),
                                departureStation = Station.서울,
                                destinationStation = Station.강릉
                        )

                        val expected = LinkedMultiValueMap<String, String>().apply {
                            this["Device"] = "AD"
                            this["radJobId"] = "1"
                            this["selGoTrain"] = "00"
                            this["txtGoAbrdDt"] = "20000101"
                            this["txtGoHour"] = "010101"
                            this["txtGoStart"] = "서울"
                            this["txtGoEnd"] = "강릉"
                            this["txtPsgFlg_1"] = "1"
                            this["Version"] = "190617001"
                        }

                        listOf(sut, expected).toTypedArray()
                    },

                    Arguments {
                        val sut = KorailTicketFindingRequestParameter(
                                departureDate = LocalDate.of(2020, 12, 8),
                                departureTime = LocalTime.of(12, 8, 30),
                                departureStation = Station.부산,
                                destinationStation = Station.동대구
                        )

                        val expected = LinkedMultiValueMap<String, String>().apply {
                            this["Device"] = "AD"
                            this["radJobId"] = "1"
                            this["selGoTrain"] = "00"
                            this["txtGoAbrdDt"] = "20201208"
                            this["txtGoHour"] = "120830"
                            this["txtGoStart"] = "부산"
                            this["txtGoEnd"] = "동대구"
                            this["txtPsgFlg_1"] = "1"
                            this["Version"] = "190617001"
                        }

                        listOf(sut, expected).toTypedArray()
                    }
            )
        }
    }
}