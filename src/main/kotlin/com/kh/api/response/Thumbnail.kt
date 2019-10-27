package com.kh.api.response

import com.fasterxml.jackson.annotation.JsonInclude

data class Thumbnail(
        val imageUrl: String,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        val link: Link?,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        val fixedRatio: Boolean?,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        val width: Int?,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        val height: Int?
)