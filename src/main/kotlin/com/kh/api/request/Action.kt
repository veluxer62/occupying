package com.kh.api.request

data class Action(
        val name: String,
        val clientExtra: Any?,
        val params: Params,
        val id: String,
        val detailParams: Map<String, DetailParam>
)