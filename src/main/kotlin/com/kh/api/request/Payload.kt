package com.kh.api.request

data class Payload(
        val name: String,
        val clientExtra: Map<String, Any>?,
        val params: Map<String, Any>,
        val id: String,
        val detailParams: Map<String, DetailParam>?
)