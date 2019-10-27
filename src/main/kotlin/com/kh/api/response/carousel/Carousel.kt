package com.kh.api.response.carousel

import com.kh.api.response.basicCard.BasicCard

data class Carousel(
        val type: CarouselType,
        val items: List<BasicCard>
)