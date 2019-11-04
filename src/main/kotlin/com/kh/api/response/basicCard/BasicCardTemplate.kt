package com.kh.api.response.basicCard

import com.kh.api.request.SkillPayload
import com.kh.api.response.Buttons
import com.kh.api.response.buttonsExtra.ReserveRetryExtra
import com.kh.api.response.listCard.ActionType

data class BasicCardTemplate<T>(
        val basicCard: BasicCard<T>
) {
    companion object {
        fun fromThrowable(e: Throwable): BasicCardTemplate<Any> {
            val message = if (e.message != null)
                e.message.orEmpty()
            else
                e.cause?.message.orEmpty()

            return BasicCardTemplate(
                    basicCard = BasicCard(
                            title = "열차 조회를 실패",
                            description = """
                                        $message
                                        열차 조회를 다시 해주시기 바랍니다.
                                    """.trimIndent(),
                            thumbnail = null,
                            buttons = listOf(
                                    Buttons(
                                            label = "다시 조회",
                                            action = ActionType.block,
                                            messageText = "열차조회"
                                    )
                            )
                    )
            )
        }

        fun fromReserveSkillPayload(payload: SkillPayload):
                BasicCardTemplate<ReserveRetryExtra> {
            return BasicCardTemplate(
                    BasicCard(
                            title = "예약 신청 완료",
                            description = """
                                예약 신청 완료하였습니다.
                                예약 신청에 대한 결과는 입력하신 메일로 발송될 예정입니다.
                                매진 예약인 경우 최대 30분 동안 예약 시도하며 좌석 상황에 따라 예약이 성공하지 못할 수 있습니다.
                                좌석 예약에 실패한 경우 아래 "다시 예약신청" 버튼을 클릭하여 다시 예약을 시도해 보시기 바랍니다.
                            """.trimIndent(),
                            thumbnail = null,
                            buttons = listOf(
                                    Buttons(
                                            label = "다시 예약신청",
                                            action = ActionType.block,
                                            messageText = "다시예약신청",
                                            extra = ReserveRetryExtra.fromSkillPayload(payload)
                                    )
                            )
                    )
            )
        }
    }
}