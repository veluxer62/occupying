package com.kh.api.response.carousel

import com.kh.occupying.domain.Train
import com.kh.occupying.dto.response.CommonResponse
import com.kh.occupying.dto.response.SearchResponse

data class CarouselTemplate(
        val carousel: Carousel
) {
    companion object {
        fun fromResponse(response: CommonResponse): CarouselTemplate {
            val carousel = Carousel(
                    type = CarouselType.basicCard,
                    items = if (response is SearchResponse) {
                        response.train.items.map {
                            BasicCard.fromTrain(Train.fromDto(it))
                        }
                    } else {
                        listOf()
                    }
            )

            return CarouselTemplate(carousel)
        }
    }
}