package com.kh.deprecatedOccupying.dto.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class FailResponse(
        @JsonProperty("h_msg_txt")
        override val responseMessage: String,
        @JsonProperty("h_msg_cd")
        override val responseCode: String,
        @JsonProperty("strResult")
        override val resultCode: ResultCode
) : CommonResponse
