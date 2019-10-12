package com.kh.api.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.kh.occupying.dto.param.SearchParams
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class SearchTrainParams(
        @JsonProperty("departure-time")
        val departureTime: String,
        @JsonProperty("destination-station")
        val destinationStation: String,
        @JsonProperty("departure-date")
        val departureDate: String,
        @JsonProperty("departure-station")
        val departureStation: String
) {
        fun getSearchParams(): SearchParams {
                val departureDateTime = LocalDateTime.parse(
                        departureDate + departureTime,
                        DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
                )
                return SearchParams(
                        departureDatetime = departureDateTime,
                        destinationStation = destinationStation,
                        departureStation = departureStation
                )
        }
}