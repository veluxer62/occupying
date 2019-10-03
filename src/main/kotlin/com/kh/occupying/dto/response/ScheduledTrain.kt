package com.kh.occupying.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class ScheduledTrain(
        @JsonProperty("trn_info")
        val items: List<Train>
)
