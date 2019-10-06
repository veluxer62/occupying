package com.kh.api.request

data class UserRequest(
        val timezone: String,
        val params: UserRequestParams,
        val block: Block,
        val utterance: String,
        val lang: String?,
        val user: User
)