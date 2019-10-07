package com.kh.api.response.listCard

import com.kh.occupying.domain.Train

data class ButtonsItem(
        val label: String,
        val action: ActionType,
        val webLinkUrl: String
) {
    companion object {
        fun fromTrain(train: Train): ButtonsItem {
            return ButtonsItem(
                    label = train.no,
                    action = ActionType.webLink,
                    webLinkUrl = "http://naver.com"
            )
        }
    }
}