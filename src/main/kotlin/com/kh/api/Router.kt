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
    fun kakaoSkillRoutes(kakaoSkillHandler: KakaoSkillHandler, webApiHandler: WebApiHandler) = router {
        "/api/kakao".nest {
            accept(MediaType.APPLICATION_JSON_UTF8).nest {
                POST("/find-trains", kakaoSkillHandler::findTrains)
                POST("/reserve-train", kakaoSkillHandler::reserveTrain)
                POST("/retry-train-reservation", kakaoSkillHandler::retryTrainReservation)
            }
        }

        "/api/web".nest {
            accept(MediaType.APPLICATION_JSON).nest {
                GET("/find-trains", webApiHandler::findTrains)
                POST("/reserve-train", webApiHandler::reserveTrain)
                POST("/retry-train-reservation", webApiHandler::retryTrainReservation)
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
