package com.kh.occupying.domain

data class Passenger(
        val type: String,
        val headCount: Int,
        val discount: Discount
)