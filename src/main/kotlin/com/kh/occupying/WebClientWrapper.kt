package com.kh.occupying

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.kh.occupying.dto.FindTrainResult
import com.kh.occupying.dto.LoginResult
import org.springframework.core.ParameterizedTypeReference
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Mono
import java.net.URI

class WebClientWrapper(private val properties: KorailProperties) {

    fun <T> get(uri: (UriBuilder) -> URI,
                responseType: TypeReference<T>): Mono<T> {
        return WebClient.create("${properties.host}${properties.contextPath}")
                .get()
                .uri(uri)
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
                .retrieve()
                .bodyToMono(String::class.java)
                .map {
                    ObjectMapper().readValue<T>(it, responseType)
                }
    }

}