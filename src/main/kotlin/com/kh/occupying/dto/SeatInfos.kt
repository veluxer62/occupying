package com.kh.occupying.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class SeatInfos(
        @JsonProperty("seat_info")
        val seat_info: List<SeatInfo>
)
