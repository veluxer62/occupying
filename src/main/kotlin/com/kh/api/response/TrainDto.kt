package com.kh.api.response

import com.kh.deprecatedOccupying.domain.Station
import com.kh.deprecatedOccupying.domain.Train

data class TrainDto(
    val no: String,
    val trainClass: String,
    val departureDate: String,
    val departureTime: String,
    val arrivalTime: String,
    val departureStation: Station,
    val destinationStation: Station,
    val fee: Int,
    val seat: String
) {
    companion object {
        fun from(train: Train) = TrainDto(
            no = train.no,
            trainClass = train.trainClass.label,
            departureDate = train.departureDate.toString(),
            departureTime = train.departureTime.toString(),
            arrivalTime = train.arrivalTime.toString(),
            destinationStation = train.departureStation,
            departureStation = train.destinationStation,
            fee = train.fee,
            seat = train.seatClass
        )
    }
}
