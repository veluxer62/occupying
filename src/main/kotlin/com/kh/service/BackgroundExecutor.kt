package com.kh.service

import com.kh.api.request.ReservationParams
import com.kh.api.request.SearchTrainParams
import com.kh.api.request.SkillPayload
import com.kh.occupying.Korail
import com.kh.occupying.dto.response.LoginResponse
import com.kh.util.mapTo
import org.springframework.scheduling.annotation.Async
import reactor.core.publisher.Mono

open class BackgroundExecutor(
        private val korail: Korail,
        private val alarmSender: AlarmSender
) {
    @Async
    open fun reserveTrain(it: SkillPayload) {
        val clientExtra = it.action.clientExtra.orEmpty()
        val searchPayload = clientExtra
                .mapTo<SearchTrainParams>()
                .getSearchParams()
        val findTrains = korail.findAvailableTrain(
                params = searchPayload,
                trainNo = clientExtra["train-no"].toString(),
                retryCount = 1800
        )

        val reservationPayload = it.action.params.mapTo<ReservationParams>()
        val loginResult = korail.login(
                id = reservationPayload.id,
                pw = reservationPayload.pw
        )

        try {
            Mono.zip(findTrains, loginResult)
                    .flatMap { x ->
                        require(x.t2 is LoginResponse) {
                            x.t2.responseMessage
                        }

                        val login = (x.t2 as LoginResponse).toDomain()
                        val train = x.t1
                        korail.reserve(login, train)
                    }
                    .block()
            alarmSender.sendSuccessMessage(reservationPayload.email)
        } catch (e: Exception) {
            alarmSender.sendFailMessage(reservationPayload.email)
        }
    }
}