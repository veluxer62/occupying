package com.kh.api.response.carousel

import com.fasterxml.jackson.annotation.JsonIgnore
import com.kh.api.response.Link

data class Thumbnail(
        val imageUrl: String,

        @JsonIgnore(value = false)
        val link: Link? = null,

        @JsonIgnore(value = false)
        val fixedRatio: Boolean? = null,

        @JsonIgnore(value = false)
        val width: Int? = null,

        @JsonIgnore(value = false)
        val height: Int? = null
)