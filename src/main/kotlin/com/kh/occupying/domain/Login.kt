package com.kh.occupying.domain

data class Login(
        val key: String,
        val cookie: List<String>
) {
    fun generateHeaders(): Map<String, String> {
        return mapOf(
                "Cookie" to cookie.joinToString(";")
        )
    }
}
