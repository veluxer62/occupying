package com.kh.occupying

interface Alarm {
    fun <T> ring(message: T, to: String)
}