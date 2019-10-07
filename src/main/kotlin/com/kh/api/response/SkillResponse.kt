package com.kh.api.response

data class SkillResponse<T>(
        val version: String,
        val template: OutPuts<T>
)