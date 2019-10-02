package com.kh.occupying.domain

import com.kh.occupying.dto.Train as Dto
import java.time.LocalDate
import java.time.LocalTime

data class Train(
        val no: String,
        val trainGroup: String,
        val trainClass: String,
        val runDate: LocalDate,
        val departureDate: LocalDate,
        val departureTime: LocalTime,
        val departureStationCode: String,
        val destinationCode: String,
        val seatClass: String,
        val coachSeatCode: String,
        val passenger: Passenger
) {
    companion object {
        fun fromDto(dto: Dto): Train {
            return Train(
                    no = dto.trnNo,
                    trainGroup = dto.trnGpCd,
                    trainClass = dto.trainClass,
                    runDate = dto.runDate,
                    departureDate = dto.departureDate,
                    departureTime = dto.departureTime,
                    departureStationCode = dto.departureStationCode,
                    destinationCode = dto.destinationCode,
                    seatClass = "1",
                    coachSeatCode = dto.genRsvCd,
                    passenger = Passenger(
                            type = "1",
                            headCount = 1,
                            discount = Discount()
                    )
            )
        }
    }
}