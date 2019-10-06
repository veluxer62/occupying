package com.kh.api.request

data class User(
        val id: String,
        val type: String,
        val properties: Map<String, Any>
)