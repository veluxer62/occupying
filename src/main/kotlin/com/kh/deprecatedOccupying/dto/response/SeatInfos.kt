package com.kh.deprecatedOccupying.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class SeatInfos(
        @JsonProperty("seat_info")
        val seatInfo: List<SeatInfo>
)
