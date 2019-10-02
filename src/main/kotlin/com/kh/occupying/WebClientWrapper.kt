package com.kh.occupying

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Mono
import java.net.URI

class WebClientWrapper(private val properties: KorailProperties) {
    private var cookie: List<String> = listOf()

    fun <T> get(uri: (UriBuilder) -> URI,
                responseType: TypeReference<T>): Mono<T> {
        return WebClient.create("${properties.host}${properties.contextPath}")
                .get()
                .uri(uri)
                .header("Cookie", cookie.joinToString(";"))
                .retrieve()
                .bodyToMono(String::class.java)
                .map {
                    ObjectMapper().readValue<T>(it.orEmpty(), responseType)
                }
    }

    fun <T> post(uri: (UriBuilder) -> URI,
                 responseType: TypeReference<T>): Mono<T> {
        return WebClient
                .create("${properties.host}${properties.contextPath}")
                .post()
                .uri(uri)
                .exchange()
                .flatMap {
                    cookie = it.headers().header("Set-Cookie")
                    it.bodyToMono(String::class.java)
                }
                .map {
                    ObjectMapper().readValue<T>(it, responseType)
                }
    }

}