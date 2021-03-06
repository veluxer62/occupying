package com.kh.occupying.korail

enum class SeatStatus(val code: String, val label: String) {
    NONE("00", "없음"),
    AVAILABLE("11", "예약 가능"),
    SOLD_OUT("13", "매진")
}
