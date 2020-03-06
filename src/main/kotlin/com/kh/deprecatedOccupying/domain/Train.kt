package com.kh.deprecatedOccupying.domain

import java.time.LocalDate
import java.time.LocalTime

data class Train(
        val no: String,
        val trainGroup: String,
        val trainClass: TrainClass,
        val runDate: LocalDate,
        val departureDate: LocalDate,
        val departureTime: LocalTime,
        val departureStation: Station,
        val arrivalDate: LocalDate,
        val arrivalTime: LocalTime,
        val destinationStation: Station,
        val seatClass: String,
        val coachSeatCode: SeatCode,
        val passenger: Passenger,
        val fee: Int
) {
    fun hasSeat(): Boolean {
        return coachSeatCode == SeatCode.AVAILABLE
    }
}