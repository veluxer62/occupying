package com.kh.api

import com.kh.occupying.Korail
import com.kh.occupying.KorailProperties
import com.kh.occupying.WebClientWrapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Config {

    @Bean
    fun korail() = Korail(WebClientWrapper(KorailProperties()))

}