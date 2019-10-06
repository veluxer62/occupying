package com.kh.occupying.domain

import com.kh.occupying.dto.response.Train as Dto
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
        val passenger: Passenger
) {
    companion object {
        fun fromDto(dto: Dto): Train {
            return Train(
                    no = dto.trnNo,
                    trainGroup = dto.trnGpCd,
                    trainClass = TrainClass.values()
                            .first { it.code == dto.trainClassCode },
                    runDate = dto.runDate,
                    departureDate = dto.departureDate,
                    departureTime = dto.departureTime,
                    departureStation = Station.values()
                            .first { it.code == dto.departureStationCode },
                    arrivalDate = dto.arrivalDate,
                    arrivalTime = dto.arrivalTime,
                    destinationStation = Station.values()
                            .first { it.code == dto.destinationCode },
                    seatClass = "1",
                    coachSeatCode = when(dto.genRsvCd) {
                        "00" -> SeatCode.NONE
                        "11" -> SeatCode.AVAILABLE
                        else -> SeatCode.SOLD_OUT
                    },
                    passenger = Passenger(
                            type = "1",
                            headCount = 1,
                            discount = Discount()
                    )
            )
        }
    }
}