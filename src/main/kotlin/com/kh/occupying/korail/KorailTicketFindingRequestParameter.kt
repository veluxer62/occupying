package com.kh.occupying.korail

import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class KorailTicketFindingRequestParameter(
        val departureDate: LocalDate,
        val departureTime: LocalTime,
        val departureStation: Station,
        val destinationStation: Station
) {
    fun getQueryParams(): MultiValueMap<String, String> {
        return LinkedMultiValueMap<String, String>().apply {
            this["Device"] = "AD"
            this["radJobId"] = "1"
            this["selGoTrain"] = "00"
            this["txtGoAbrdDt"] = departureDate
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd"))
            this["txtGoHour"] = departureTime
                    .format(DateTimeFormatter.ofPattern("HHmmss"))
            this["txtGoStart"] = departureStation.name
            this["txtGoEnd"] = destinationStation.name
            this["txtPsgFlg_1"] = "1"
            this["Version"] = "190617001"
        }
    }
}