package com.kh.api.exception

class IllegalParameterException(
        display: String,
        value: String,
        e: Throwable? = null
) : IllegalArgumentException("[$display] '$value'값이 올바르지 않습니다.", e)
