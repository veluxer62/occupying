package com.kh.api

import com.kh.api.request.SkillPayload
import com.kh.api.response.SkillResponse
import com.kh.api.response.OutPuts
import com.kh.api.response.carousel.CarouselTemplate
import com.kh.occupying.Korail
import com.kh.occupying.dto.response.CommonResponse
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class KakaoSkillHandler(val korail: Korail) {

    fun findTrains(req: ServerRequest): Mono<ServerResponse> {
        return req.bodyToMono(SkillPayload::class.java)
                .flatMap {
                    findTrains(it)
                }.flatMap {
                    ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .syncBody(makeBody(it))
                }
                .switchIfEmpty(ServerResponse.notFound().build())
    }

    private fun makeBody(it: CommonResponse): SkillResponse<CarouselTemplate> {
        val template = OutPuts(
                outputs = listOf(
                        CarouselTemplate.fromResponse(it)
                )
        )
        return SkillResponse(
                version = "2.0",
                template = template
        )
    }

    private fun findTrains(it: SkillPayload): Mono<CommonResponse> {
        val params = it.action.params
        val departureAt = LocalDateTime.parse(
                params.departureDate + params.departureTime,
                DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))

        return korail.search(
                departureAt = departureAt,
                departureStation = params.departureStation,
                destination = params.destination
        )
    }

}
