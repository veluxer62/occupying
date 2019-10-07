package com.kh.api.response.listCard

import com.kh.occupying.domain.Train
import com.kh.occupying.dto.response.CommonResponse
import com.kh.occupying.dto.response.SearchResponse

data class ListCardTemplate(
        val listCard: ListCard
) {
    companion object {
        fun fromCommonResponse(response: CommonResponse): ListCardTemplate {
            return if (response is SearchResponse) {
                val trains = response.train.items
                        .map { Train.fromDto(it) }
                ListCardTemplate(
                        listCard = ListCard(
                                header = Header(
                                        "열차 조회 결과 입니다.",
                                        ""
                                ),
                                items = trains.map {
                                    ItemsItem.fromTrain(it)
                                }.take(5),
                                buttons = trains.map {
                                    ButtonsItem.fromTrain(it)
                                }.take(2)
                        )
                )
            } else {
                ListCardTemplate(
                        listCard = ListCard(
                                header = Header(
                                        "조회된 열차가 존재하지 않습니다.",
                                        ""
                                ),
                                items = listOf(),
                                buttons = listOf()
                        )
                )
            }
        }
    }
}