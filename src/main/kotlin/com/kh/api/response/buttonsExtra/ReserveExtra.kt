package com.kh.api.response.buttonsExtra

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.kh.deprecatedOccupying.domain.Station
import java.time.LocalDate
import java.time.LocalTime

data class ReserveExtra(
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
        val destinationStation: Station
)