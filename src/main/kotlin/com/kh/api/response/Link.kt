package com.kh.api.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include

data class Link(
        @JsonInclude(Include.NON_NULL)
        val mobile: String? = null,

        @JsonInclude(Include.NON_NULL)
        val ios: String? = null,

        @JsonInclude(Include.NON_NULL)
        val android: String? = null,

        @JsonInclude(Include.NON_NULL)
        val pc: String? = null,

        @JsonInclude(Include.NON_NULL)
        val mac: String? = null,

        @JsonInclude(Include.NON_NULL)
        val win: String? = null,

        @JsonInclude(Include.NON_NULL)
        val web: String? = null
)