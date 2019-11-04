package com.kh.api.response.buttonsExtra

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.kh.api.request.ReservationParams
import com.kh.api.request.SearchTrainParams
import com.kh.api.request.SkillPayload
import com.kh.occupying.domain.Station
import com.kh.util.mapTo
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class ReserveRetryExtra(
        @JsonProperty("departure-date")
        @JsonFormat(pattern = "yyyyMMdd")
        val departureDate: LocalDate,
        @JsonProperty("departure-time")
        @JsonFormat(pattern = "HHmmss")
        val departureTime: LocalTime,
        @JsonProperty("train-no")
        val trainNo: String,
        @JsonProperty("departure-station")
        val departureStation: Station,
        @JsonProperty("destination-station")
        val destinationStation: Station,
        @JsonProperty("id")
        val id: String,
        @JsonProperty("pw")
        val pw: String,
        @JsonProperty("email")
        val email: String
) {
    companion object {
        fun fromSkillPayload(payload: SkillPayload): ReserveRetryExtra {
            val reserveParams = payload.action.params.mapTo<ReservationParams>()
            val searchParams = payload.action.clientExtra!!.mapTo<SearchTrainParams>()
            val trainNo = payload.action.clientExtra["train-no"].toString()
            return ReserveRetryExtra(
                    departureDate = LocalDate.parse(searchParams.departureDate,
                            DateTimeFormatter.ofPattern("yyyyMMdd")),
                    departureTime = LocalTime.parse(searchParams.departureTime,
                            DateTimeFormatter.ofPattern("HHmmss")),
                    departureStation = Station.valueOf(searchParams.departureStation),
                    destinationStation = Station.valueOf(searchParams.destinationStation),
                    trainNo = trainNo,
                    id = reserveParams.id,
                    pw = reserveParams.pw,
                    email = reserveParams.email
            )
        }
    }
}