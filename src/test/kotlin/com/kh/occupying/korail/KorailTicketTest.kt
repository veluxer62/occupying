package com.kh.occupying.korail

import com.kh.occupying.Ticket
import com.kh.testingHelper.Fixture
import com.kh.testingHelper.FixtureExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class KorailTicketTest {

    @Test
    @ExtendWith(FixtureExtension::class)
    fun `KorailTicket is implemented Ticket`(@Fixture sut: KorailTicket) {
        assertThat(sut).isInstanceOf(Ticket::class.java)
    }

    @ParameterizedTest
    @ArgumentsSource(SeatStatusArguments::class)
    @ExtendWith(FixtureExtension::class)
    fun `isAvailable return boolean correctly if given seatStatus`(
            seatStatus: SeatStatus, expected: Boolean,
            @Fixture ticket: KorailTicket) {
        val sut = ticket.copy(seatStatus = seatStatus)

        val actual = sut.isAvailable()

        assertThat(actual).isEqualTo(expected)
    }

    internal class SeatStatusArguments : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
            return Stream.of(
                    Arguments.of(SeatStatus.NONE, false),
                    Arguments.of(SeatStatus.AVAILABLE, true),
                    Arguments.of(SeatStatus.SOLD_OUT, false)
            )
        }
    }

}