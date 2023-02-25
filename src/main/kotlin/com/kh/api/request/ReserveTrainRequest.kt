package com.kh.api.request

import com.kh.deprecatedOccupying.domain.Station
import com.kh.deprecatedOccupying.dto.param.SearchParams
import java.time.LocalDateTime

data class ReserveTrainRequest(
    val user: User,
    val trainNo: String,
    val searchTrainRequest: SearchTrainRequest
) {
    data class User(
        val id: String,
        val password: String,
        val email: String
    ) {
        fun toParam() = ReservationParams(id, password, email)
    }

    data class SearchTrainRequest(
        val departureDateTime: LocalDateTime,
        val departureStation: Station,
        val destinationStation: Station
    ) {
        fun toParam() = SearchParams(departureDateTime, departureStation, destinationStation)
    }
}
