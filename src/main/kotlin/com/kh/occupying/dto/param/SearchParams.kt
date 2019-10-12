package com.kh.occupying.dto.param

import java.time.LocalDateTime

data class SearchParams(
        val departureDatetime: LocalDateTime,
        val departureStation: String,
        val destinationStation: String
)
