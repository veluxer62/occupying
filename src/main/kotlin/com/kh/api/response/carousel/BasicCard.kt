package com.kh.api.response.carousel

import com.kh.api.response.Buttons
import com.kh.api.response.buttonsExtra.ReserveExtra
import com.kh.api.response.listCard.ActionType
import com.kh.occupying.domain.Train
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class BasicCard(
        val title: String,
        val description: String,
        val thumbnail: Thumbnail,
        val buttons: List<Buttons<ReserveExtra>>
) {
    companion object {
        fun fromTrain(train: Train): BasicCard {
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            val title = "[${train.trainClass.label}]${train.no} " +
                    "[${train.departureStation}]${train.departureTime.format(formatter)}~" +
                    "[${train.destinationStation}]${train.arrivalTime.format(formatter)}"
            val description = "${train.fee}원 [${train.coachSeatCode.label}]"
            val extra = ReserveExtra(
                    departureDate = train.departureDate,
                    departureTime = train.departureTime,
                    trainNo = train.no,
                    departureStation = train.departureStation,
                    destinationStation = train.destinationStation
            )
            val buttons = listOf(
                    Buttons(
                            label = "예약하기",
                            action = ActionType.block,
                            messageText = "예약하기",
                            extra = extra
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