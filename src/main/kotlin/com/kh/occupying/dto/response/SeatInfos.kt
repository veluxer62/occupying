package com.kh.occupying.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class SeatInfos(
        @JsonProperty("seat_info")
        val seatInfo: List<SeatInfo>
)
