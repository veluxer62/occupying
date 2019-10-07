package com.kh.api.response

import com.fasterxml.jackson.annotation.JsonIgnore

data class Link(
        @JsonIgnore(value = false)
        val mobile: String? = null,

        @JsonIgnore(value = false)
        val ios: String? = null,

        @JsonIgnore(value = false)
        val android: String? = null,

        @JsonIgnore(value = false)
        val pc: String? = null,

        @JsonIgnore(value = false)
        val mac: String? = null,

        @JsonIgnore(value = false)
        val win: String? = null,

        @JsonIgnore(value = false)
        val web: String? = null
)