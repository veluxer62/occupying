package com.kh.api.response

import com.fasterxml.jackson.annotation.JsonIgnore
import com.kh.api.response.listCard.ActionType

data class Buttons(
        val label: String,

        val action: ActionType,

        @JsonIgnore(value = false)
        val webLinkUrl: String? = null,

        @JsonIgnore(value = false)
        val osLink: Link? = null,

        @JsonIgnore(value = false)
        val messageText: String? = null,

        @JsonIgnore(value = false)
        val phoneNumber: String? = null,

        @JsonIgnore(value = false)
        val blockId: String? = null,

        @JsonIgnore(value = false)
        val extra: Map<String, Any>? = null
)