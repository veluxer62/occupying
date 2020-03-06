package com.kh.deprecatedOccupying.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class JrnyInfos(
        @JsonProperty("jrny_info")
        val jrnyInfo: List<JrnyInfo>
)
