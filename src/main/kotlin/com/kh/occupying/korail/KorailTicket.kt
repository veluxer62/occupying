package com.kh.occupying.korail

import com.kh.occupying.Ticket
import java.math.BigDecimal
import java.time.LocalDateTime

data class KorailTicket(
        val id: String,
        val trainClass: TrainClass,
        val departureDateTime: LocalDateTime,
        val departureStation: Station,
        val arrivalDateTime: LocalDateTime,
        val destinationStation: Station,
        val seatClass: SeatClass,
        val seatStatus: SeatStatus,
        val passenger: Passenger,
        val price: BigDecimal
) : Ticket {
    override fun isAvailable(): Boolean {
        return seatStatus == SeatStatus.AVAILABLE
    }
}