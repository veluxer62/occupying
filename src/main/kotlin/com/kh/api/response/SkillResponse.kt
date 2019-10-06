package com.kh.api.response

import com.kh.occupying.domain.Train
import com.kh.occupying.dto.response.CommonResponse
import com.kh.occupying.dto.response.SearchResponse

data class SkillResponse(
        val version: String,
        val template: Template
) {
    companion object {
        fun fromCommonResponse(response: CommonResponse): SkillResponse {
            return if (response is SearchResponse) {
                val trains = response.train.items
                        .map { Train.fromDto(it) }
                SkillResponse(
                        version = "2.0",
                        template = Template(
                                outputs = listOf(
                                        OutputsItem(
                                                listCard = ListCard(
                                                        header = Header(
                                                                "열차 조회 결과 입니다",
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
                                )
                        )
                )
            } else {
                TODO()
            }
        }
    }
}