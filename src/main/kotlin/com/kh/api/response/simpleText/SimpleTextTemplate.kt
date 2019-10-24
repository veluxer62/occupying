package com.kh.api.response.simpleText

data class SimpleTextTemplate(
        val simpleText: SimpleText
) {
    companion object {
        fun of(text: String): SimpleTextTemplate {
            return SimpleTextTemplate(SimpleText(text))
        }

        fun fromThrowable(
                e: Throwable,
                messageWrapper: (message: String) -> String
        ): SimpleTextTemplate {
            val message = if (e.message != null)
                e.message.orEmpty()
            else
                e.cause?.message.orEmpty()

            return of(messageWrapper(message))
        }
    }
}