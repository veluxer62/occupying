package com.kh.api.request

import com.fasterxml.jackson.annotation.JsonProperty

data class Params(
        @JsonProperty("departure-time")
        val departureTime: String,
        @JsonProperty("destination")
        val destination: String,
        @JsonProperty("departure-date")
        val departureDate: String,
        @JsonProperty("departure-station")
        val departureStation: String
)