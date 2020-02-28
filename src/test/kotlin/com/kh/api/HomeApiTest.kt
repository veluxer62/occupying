package com.kh.api

import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

internal class HomeApiTest {

    
    private val route = Router().homeRoute()
    private val client = WebTestClient.bindToRouterFunction(route).build()

    @Test
    fun `root path will return response ok`() {
        client.get()
                .uri("/")
                .exchange()
                .expectStatus()
                .isOk
    }

}