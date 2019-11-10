package com.kh.api

import com.kh.api.request.ReservationParams
import com.kh.api.request.SearchTrainParams
import com.kh.api.request.SkillPayload
import com.kh.api.response.OutPuts
import com.kh.api.response.SkillResponse
import com.kh.api.response.basicCard.BasicCardTemplate
import com.kh.api.response.carousel.CarouselTemplate
import com.kh.api.response.simpleText.SimpleTextTemplate
import com.kh.occupying.Korail
import com.kh.occupying.dto.response.SearchResponse
import com.kh.service.AlarmSender
import com.kh.service.BackgroundExecutor
import com.kh.util.mapTo
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

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
                    require(it is SearchResponse) {
                        it.responseMessage
                    }
                    response(CarouselTemplate.fromResponse(it))
                }.onErrorResume {
                    response(BasicCardTemplate.fromThrowable(it))
                }
    }

    fun reserveTrain(req: ServerRequest): Mono<ServerResponse> {
        return req.bodyToMono(SkillPayload::class.java)
                .doOnNext {
                    it.action.params.mapTo<ReservationParams>()
                }
                .doOnNext {
                    val (
                            searchPayload,
                            trainNo,
                            reservationPayload
                    ) = getReservationPayloads(it)

                    backgroundExecutor.reserveTrain(
                            searchPayload.getSearchParams(), trainNo, reservationPayload)
                }
                .flatMap {
                    val (
                            searchPayload,
                            trainNo,
                            reservationPayload
                    ) = getReservationPayloads(it)

                    response(BasicCardTemplate.fromReserveSkillPayload(
                            searchPayload, trainNo, reservationPayload))
                }.onErrorResume {
                    response(SimpleTextTemplate.fromThrowable(it) { message ->
                        """
                            $message
                            예약 신청을 다시 해주시기 바랍니다.
                        """.trimIndent()
                    })
                }
    }

    private fun getReservationPayloads(it: SkillPayload):
            Triple<SearchTrainParams, String, ReservationParams> {
        val clientExtra = it.action.clientExtra.orEmpty()
        val searchPayload = clientExtra
                .mapTo<SearchTrainParams>()
        val trainNo = clientExtra["train-no"].toString()
        val reservationPayload = it.action.params
                .mapTo<ReservationParams>()

        return Triple(searchPayload, trainNo, reservationPayload)
    }

    fun retryTrainReservation(req: ServerRequest): Mono<ServerResponse> {
        return req.bodyToMono(SkillPayload::class.java)
                .doOnNext {
                    val (
                            searchPayload,
                            trainNo,
                            reservationPayload
                    ) = retryReservationPayloads(it)

                    backgroundExecutor.reserveTrain(
                            searchPayload.getSearchParams(), trainNo, reservationPayload)
                }
                .flatMap {
                    val (
                            searchPayload,
                            trainNo,
                            reservationPayload
                    ) = retryReservationPayloads(it)

                    response(BasicCardTemplate.fromReserveSkillPayload(
                            searchPayload, trainNo, reservationPayload))
                }.onErrorResume {
                    response(SimpleTextTemplate.fromThrowable(it) { message ->
                        """
                            $message
                            예약 신청을 다시 해주시기 바랍니다.
                        """.trimIndent()
                    })
                }
    }

    private fun retryReservationPayloads(it: SkillPayload):
            Triple<SearchTrainParams, String, ReservationParams> {
        val clientExtra = it.action.clientExtra.orEmpty()
        val searchPayload = clientExtra
                .mapTo<SearchTrainParams>()
        val trainNo = clientExtra["train-no"].toString()
        val reservationPayload = clientExtra.mapTo<ReservationParams>()

        return Triple(searchPayload, trainNo, reservationPayload)
    }

    private fun <T> response(template: T): Mono<ServerResponse> {
        val body = SkillResponse(
                version = "2.0",
                template = OutPuts(listOf(template))
        )
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .syncBody(body)
    }
}