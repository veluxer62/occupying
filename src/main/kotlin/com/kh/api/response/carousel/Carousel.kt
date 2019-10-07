package com.kh.api.response.carousel

data class Carousel(
        val type: CarouselType,
        val items: List<BasicCard>
)