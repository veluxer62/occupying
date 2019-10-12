package com.kh.occupying.dto.param

import com.kh.occupying.domain.Station
import java.time.LocalDateTime

data class SearchParams(
        val departureDatetime: LocalDateTime,
        val departureStation: Station,
        val destinationStation: Station
)
