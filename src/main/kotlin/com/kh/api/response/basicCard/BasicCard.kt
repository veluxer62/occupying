package com.kh.api.response.basicCard

import com.fasterxml.jackson.annotation.JsonInclude
import com.kh.api.response.Buttons
import com.kh.api.response.Thumbnail
import com.kh.api.response.buttonsExtra.ReserveExtra
import com.kh.api.response.listCard.ActionType
import com.kh.occupying.domain.Train
import java.time.format.DateTimeFormatter

data class BasicCard(
        @JsonInclude(JsonInclude.Include.NON_NULL)
        val title: String,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        val description: String?,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        val thumbnail: Thumbnail?,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        val buttons: List<Buttons<ReserveExtra>>?
) {
    companion object {
        fun fromTrain(train: Train): BasicCard {
            return BasicCard(
                    title = makeTitle(train),
                    description = makeDescription(train),
                    thumbnail = Thumbnail(
                            imageUrl = "https://t1.daumcdn.net/cfile/tistory/026E244F51CB94FA0C",
                            link = null,
                            fixedRatio = null,
                            width = null,
                            height = null
                    ),
                    buttons = makeButton(train)
            )
        }

        private fun makeButton(train: Train): List<Buttons<ReserveExtra>> {
            val extra = ReserveExtra(
                    departureDate = train.departureDate,
                    departureTime = train.departureTime,
                    trainNo = train.no,
                    departureStation = train.departureStation,
                    destinationStation = train.destinationStation
            )
            return listOf(
                    Buttons(
                            label = "예약하기",
                            action = ActionType.block,
                            blockId = "5d982f9d92690d0001a43950",
                            messageText = "예약하기",
                            extra = extra
                    )
            )
        }

        private fun makeDescription(train: Train) =
                "열차[${train.no}] ${train.fee}원 [${train.coachSeatCode.label}]"

        private fun makeTitle(train: Train): String {
            val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
            val dateFormatter = DateTimeFormatter
                    .ofPattern("MM월dd일")
            return "[${train.trainClass.label}]${train.departureDate.format(dateFormatter)} " +
                    "[${train.departureStation}]${train.departureTime.format(timeFormatter)}~" +
                    "[${train.destinationStation}]${train.arrivalTime.format(timeFormatter)}"
        }
    }
}