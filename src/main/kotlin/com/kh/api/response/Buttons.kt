package com.kh.api.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.kh.api.response.listCard.ActionType

data class Buttons(
        val label: String,

        val action: ActionType,

        @JsonInclude(Include.NON_NULL)
        val webLinkUrl: String? = null,

        @JsonInclude(Include.NON_NULL)
        val osLink: Link? = null,

        @JsonInclude(Include.NON_NULL)
        val messageText: String? = null,

        @JsonInclude(Include.NON_NULL)
        val phoneNumber: String? = null,

        @JsonInclude(Include.NON_NULL)
        val blockId: String? = null,

        @JsonInclude(Include.NON_NULL)
        val extra: Map<String, Any>? = null
)