package com.kh.api.response.listCard

import com.kh.occupying.domain.Train

data class ItemsItem(
        val title: String,
        val description: String,
        val imageUrl: String,
        val link: Link
) {
    companion object {
        fun fromTrain(train: Train): ItemsItem {
            return ItemsItem(
                    title = "[${train.no}] ${train.departureDate} ${train.departureTime}, ${train.departureStation} -> ${train.destinationStation}",
                    description = "",
                    imageUrl = "",
                    link = Link("")
            )
        }
    }
}