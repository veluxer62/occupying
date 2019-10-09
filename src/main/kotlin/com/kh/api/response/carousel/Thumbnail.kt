package com.kh.api.response.carousel

import com.fasterxml.jackson.annotation.JsonIgnore
import com.kh.api.response.Link

data class Thumbnail(
        val imageUrl: String,

        @JsonIgnore(value = true)
        val link: Link? = null,

        @JsonIgnore(value = true)
        val fixedRatio: Boolean? = null,

        @JsonIgnore(value = true)
        val width: Int? = null,

        @JsonIgnore(value = true)
        val height: Int? = null
)