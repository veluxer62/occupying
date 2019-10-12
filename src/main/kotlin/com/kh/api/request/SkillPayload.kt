package com.kh.api.request

data class SkillPayload(
        val intent: Intent,
        val userRequest: UserRequest,
        val bot: Bot,
        val action: Payload
)