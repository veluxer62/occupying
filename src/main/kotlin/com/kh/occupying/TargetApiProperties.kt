package com.kh.occupying

interface TargetApiProperties {
    fun getSchema(): String
    fun getHost(): String
    fun getPort(): Int
    fun getFindPath(): String
    fun getReservePath(): String
    fun getLoginPath(): String
}