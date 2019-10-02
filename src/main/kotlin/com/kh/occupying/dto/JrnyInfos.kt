package com.kh.occupying.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class JrnyInfos(
        @JsonProperty("jrny_info")
        val jrnyInfo: List<JrnyInfo>
)
