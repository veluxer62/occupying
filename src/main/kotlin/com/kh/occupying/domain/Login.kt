package com.kh.occupying.domain

import com.kh.occupying.dto.response.LoginResponse

data class Login(
        val key: String,
        val cookie: List<String>
) {
    companion object {
        fun fromDto(dto: LoginResponse): Login {
            return Login(
                    key = dto.key,
                    cookie = dto.cookie ?: listOf()
            )
        }
    }

    fun generateHeaders(): Map<String, String> {
        return mapOf(
                "Cookie" to cookie.joinToString(";")
        )
    }
}
