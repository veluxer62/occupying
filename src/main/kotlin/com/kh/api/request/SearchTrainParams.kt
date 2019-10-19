package com.kh.api.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.kh.api.exception.IllegalParameterException
import com.kh.occupying.domain.Station
import com.kh.occupying.dto.param.SearchParams
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
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
    init {
        try {
            LocalDate.parse(departureDate,
                    DateTimeFormatter.ofPattern("yyyyMMdd"))
        } catch (e: Exception) {
            throw IllegalParameterException("출발일", departureDate)
        }

        try {
            LocalTime.parse(departureTime,
                    DateTimeFormatter.ofPattern("HHmmss"))
        } catch (e: Exception) {
            throw IllegalParameterException("출발시간", departureDate)
        }

        try {
            Station.valueOf(departureStation)
        } catch (e: Exception) {
            throw IllegalParameterException("출발역", departureStation)
        }

        try {
            Station.valueOf(destinationStation)
        } catch (e: Exception) {
            throw IllegalParameterException("도착역", destinationStation)
        }
    }

    fun getSearchParams(): SearchParams {
        val departureDateTime = LocalDateTime.parse(
                departureDate + departureTime,
                DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        )
        return SearchParams(
                departureDatetime = departureDateTime,
                destinationStation = Station.valueOf(destinationStation),
                departureStation = Station.valueOf(departureStation)
        )
    }
}