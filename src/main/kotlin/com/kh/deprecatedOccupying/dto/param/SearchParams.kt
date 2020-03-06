package com.kh.deprecatedOccupying.dto.param

import com.kh.deprecatedOccupying.domain.Station
import java.time.LocalDateTime

data class SearchParams(
        val departureDatetime: LocalDateTime,
        val departureStation: Station,
        val destinationStation: Station
)
