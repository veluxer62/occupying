package com.kh.occupying

interface Ticket {
    fun <T> getPayload(): T
    fun isAvailable(): Boolean
}