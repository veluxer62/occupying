package com.kh.api

import com.kh.api.request.SearchTrainParams
import com.kh.api.request.SkillPayload
import com.kh.api.response.OutPuts
import com.kh.api.response.SkillResponse
import com.kh.api.response.carousel.CarouselTemplate
import com.kh.api.response.simpleText.SimpleText
import com.kh.api.response.simpleText.SimpleTextTemplate
import com.kh.occupying.Korail
import com.kh.service.AlarmSender
import com.kh.service.BackgroundExecutor
import com.kh.util.mapTo
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Component
class KakaoSkillHandler(
        private val korail: Korail,
        private val alarmSender: AlarmSender,
        private val backgroundExecutor: BackgroundExecutor
) {

    fun findTrains(req: ServerRequest): Mono<ServerResponse> {
        return req.bodyToMono(SkillPayload::class.java)
                .flatMap {
                    val payload = it.action.params
                            .mapTo<SearchTrainParams>()
                            .getSearchParams()
                    korail.search(payload)
                }.flatMap {
                    val template = OutPuts(
                            outputs = listOf(
                                    CarouselTemplate.fromResponse(it)
                            )
                    )
                    val body = SkillResponse(
                            version = "2.0",
                            template = template
                    )
                    ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .syncBody(body)
                }
    }

    fun reserveTrain(req: ServerRequest): Mono<ServerResponse> {
        return req.bodyToMono(SkillPayload::class.java)
                .doOnNext {
                    backgroundExecutor.reserveTrain(it)
                }
                .flatMap {
                    val template = OutPuts(
                            outputs = listOf(
                                    SimpleTextTemplate(
                                            SimpleText("""
                                                예약 신청하였습니다.
                                                예약 신청에 대한 결과는 입력하신 메일로 발송될 예정입니다.
                                                매진 예약인 경우 최대 30분 동안 예약 시도하며 좌석 상황에 따라 예약이 성공하지 못할 수 있습니다.
                                            """.trimIndent())
                                    )
                            )
                    )
                    val body = SkillResponse(
                            version = "2.0",
                            template = template
                    )
                    ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .syncBody(body)
                }
                .subscribeOn(Schedulers.elastic())
    }
}