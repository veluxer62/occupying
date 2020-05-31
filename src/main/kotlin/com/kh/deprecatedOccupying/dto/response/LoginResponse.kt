package com.kh.deprecatedOccupying.dto.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.kh.deprecatedOccupying.domain.Login

@JsonIgnoreProperties(ignoreUnknown = true)
data class LoginResponse(
        @JsonProperty("h_msg_txt")
        override val responseMessage: String,
        @JsonProperty("h_msg_cd")
        override val responseCode: String,
        @JsonProperty("strResult")
        override val resultCode: ResultCode,
        @JsonProperty("Key")
        val key: String,
        val cookie: List<String>?
) : CommonResponse {
    fun toDomain(): Login {
        return Login(
                key = key,
                cookie = cookie ?: listOf()
        )
    }
}