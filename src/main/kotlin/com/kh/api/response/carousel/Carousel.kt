package com.kh.api.response.carousel

import com.kh.api.response.basicCard.BasicCard
import com.kh.api.response.buttonsExtra.ReserveExtra

data class Carousel(
        val type: CarouselType,
        val items: List<BasicCard<ReserveExtra>>
)