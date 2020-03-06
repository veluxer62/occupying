package com.kh.deprecatedOccupying.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class PsgInfos(
        @JsonProperty("psg_info")
        val psgInfo: List<PsgInfo>
)
