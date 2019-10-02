package com.kh.occupying.domain

import com.kh.occupying.dto.LoginResult

class Login(dto: LoginResult) {
    val key: String = dto.key
}
