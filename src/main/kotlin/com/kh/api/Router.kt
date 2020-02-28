package com.kh.api

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
@EnableWebFlux
class Router : WebFluxConfigurer {
    @Bean
    fun kakaoSkillRoutes(handler: KakaoSkillHandler) = router {
        "/api/kakao".nest {
            accept(MediaType.APPLICATION_JSON_UTF8).nest {
                POST("/find-trains", handler::findTrains)
                POST("/reserve-train", handler::reserveTrain)
                POST("/retry-train-reservation", handler::retryTrainReservation)
            }
        }
    }

    @Bean
    fun homeRoute() = router {
        GET("/") {
            ServerResponse.ok().build()
        }
    }
}