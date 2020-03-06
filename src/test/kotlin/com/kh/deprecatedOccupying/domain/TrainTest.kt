package com.kh.deprecatedOccupying.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import java.util.stream.Stream

internal class TrainTest {

    companion object {
        @JvmStatic
        fun initSeatCode(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(SeatCode.AVAILABLE, true),
                    Arguments.of(SeatCode.SOLD_OUT, false),
                    Arguments.of(SeatCode.NONE, false)
            )
        }
    }

    @ParameterizedTest
    @MethodSource("initSeatCode")
    fun `given coachSeatCode hasSeat will return boolean correctly`(
            seatCode: SeatCode,
            expected: Boolean
    ) {
        // Arrange
        val randomString = UUID.randomUUID().toString()
        val nowDate = LocalDate.now()
        val nowTime = LocalTime.now()
        val randomInt = Random().nextInt()
        val sut = Train(
                no = randomString,
                trainClass = TrainClass.KTX,
                trainGroup = randomString,
                runDate = nowDate,
                departureDate = nowDate,
                departureTime = nowTime,
                departureStation = Station.서울,
                arrivalDate = nowDate,
                arrivalTime = nowTime,
                destinationStation = Station.서울,
                seatClass = randomString,
                coachSeatCode = seatCode,
                passenger = Passenger(
                        type = randomString,
                        headCount = randomInt,
                        discount = Discount()
                ),
                fee = randomInt
        )

        // Act
        val actual = sut.hasSeat()

        // Assert
        assertThat(actual).isEqualTo(expected)
    }
}