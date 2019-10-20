package com.kh.api.response.simpleText

data class SimpleTextTemplate(
        val simpleText: SimpleText
) {
    companion object {
        fun of(text: String): SimpleTextTemplate {
            return SimpleTextTemplate(SimpleText(text))
        }

        fun fromThrowable(e: Throwable): SimpleTextTemplate {
            val message = if (e.message != null)
                e.message.orEmpty()
            else
                e.cause?.message.orEmpty()

            val text = """
                        $message
                        열차 조회를 다시 해주시기 바랍니다.
                    """.trimIndent()
            return of(text)
        }
    }
}