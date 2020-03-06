package com.kh.deprecatedOccupying.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class PsgDiscAddInfos(
        @JsonProperty("psgDiscAdd_info")
        val psgDiscAddInfo: List<Any>
)
