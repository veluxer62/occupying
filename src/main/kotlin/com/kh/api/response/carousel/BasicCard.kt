package com.kh.api.response.carousel

import com.kh.api.response.Buttons
import com.kh.api.response.listCard.ActionType
import com.kh.occupying.domain.Train
import java.time.format.DateTimeFormatter

data class BasicCard(
        val title: String,
        val description: String,
        val thumbnail: Thumbnail,
        val buttons: List<Buttons>
) {
    companion object {
        fun fromTrain(train: Train): BasicCard {
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            val title = "[${train.trainClass.label}]${train.no} " +
                    "[${train.departureStation}]${train.departureTime.format(formatter)}~" +
                    "[${train.destinationStation}]${train.arrivalTime.format(formatter)}"
            val description = "${train.fee}원 [${train.coachSeatCode.label}]"
            val buttons = listOf(
                    Buttons(
                            label = "예약하기",
                            action = ActionType.block,
                            messageText = "예약하기",
                            extra = mapOf()
                    )
            )
            val thumbnail = Thumbnail(
                    imageUrl = "https://t1.daumcdn.net/cfile/tistory/026E244F51CB94FA0C"
            )
            return BasicCard(
                    thumbnail = thumbnail,
                    title = title,
                    description = description,
                    buttons = buttons
            )
        }
    }
}