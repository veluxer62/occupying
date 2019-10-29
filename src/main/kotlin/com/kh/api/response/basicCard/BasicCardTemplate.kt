package com.kh.api.response.basicCard

import com.kh.api.response.Buttons
import com.kh.api.response.listCard.ActionType

data class BasicCardTemplate(
        val basicCard: BasicCard
) {
    companion object {
        fun fromThrowable(e: Throwable): BasicCardTemplate {
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
    }
}