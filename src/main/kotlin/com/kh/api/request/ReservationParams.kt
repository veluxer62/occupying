package com.kh.api.request

import com.kh.api.exception.IllegalParameterException
import com.kh.util.isEmail

data class ReservationParams(
        val id: String,
        val pw: String,
        val email: String
) {
    init {
        if (id.isEmpty())
            throw IllegalParameterException("ID", id)

        if (pw.isEmpty())
            throw IllegalParameterException("비밀번호", pw)

        if (email.isEmpty() || !email.isEmail())
            throw IllegalParameterException("이메일", email)
    }
}