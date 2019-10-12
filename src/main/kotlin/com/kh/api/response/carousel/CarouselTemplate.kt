package com.kh.api.response.carousel

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
                        makeCarouselTemplate(response)
                    } else {
                        listOf()
                    }
            )

            return CarouselTemplate(carousel)
        }

        private fun makeCarouselTemplate(response: SearchResponse): List<BasicCard> {
            return response.train.items.map {
                BasicCard.fromTrain(it.toDomain())
            }
        }
    }
}