package com.kh.api.response.simpleText

import com.kh.occupying.dto.response.CommonResponse
import com.kh.occupying.dto.response.ReservationResponse

data class SimpleTextTemplate(
        val simpleText: SimpleText
) {
    companion object {
        fun fromResponse(response: CommonResponse): SimpleTextTemplate {
            val text = if (response is ReservationResponse) {
                "예약 성공하였습니다."
            } else {
                "예약 실패하였습니다."
            }

            return SimpleTextTemplate(SimpleText(text))
        }
    }
}