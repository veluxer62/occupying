package com.kh.occupying

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.kh.occupying.dto.response.CommonResponse
import com.kh.occupying.dto.response.FailResponse
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Mono
import java.net.URI

class WebClientWrapper(private val properties: KorailProperties) {
    var cookie: List<String> = listOf()

    fun <T : CommonResponse> get(uri: (UriBuilder) -> URI,
                responseType: TypeReference<T>,
                headers: Map<String, String> = mapOf()): Mono<CommonResponse> {
        return WebClient.create("${properties.host}${properties.contextPath}")
                .get()
                .uri(uri)
                .headers {
                    headers.keys.forEach {key ->
                        it.set(key, headers[key])
                    }
                }
                .retrieve()
                .bodyToMono(String::class.java)
                .map {
                    val mapper = jacksonObjectMapper()
                    try {
                        mapper.readValue<T>(it.orEmpty(), responseType)
                    } catch (e: JsonMappingException) {
                        mapper.readValue(it, FailResponse::class.java)
                    }
                }
    }

    fun <T : CommonResponse> post(uri: (UriBuilder) -> URI,
                 responseType: TypeReference<T>): Mono<CommonResponse> {
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
                    val mapper = jacksonObjectMapper()
                    try {
                        mapper.readValue<T>(it, responseType)
                    } catch (e: JsonMappingException) {
                        mapper.readValue(it, FailResponse::class.java)
                    }
                }
    }

}