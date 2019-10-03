package com.kh.occupying.domain

import com.kh.occupying.dto.response.LoginResponse

class Login(dto: LoginResponse) {
    val key: String = dto.key
}
