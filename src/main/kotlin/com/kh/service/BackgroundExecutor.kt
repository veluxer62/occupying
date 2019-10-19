package com.kh.service

import com.kh.api.request.LoginParams
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

        val loginPayload = it.action.params.mapTo<LoginParams>()
        val loginResult = korail.login(
                id = loginPayload.id,
                pw = loginPayload.pw
        ).map { response ->
            (response as LoginResponse).toDomain()
        }

        Mono.zip(findTrains, loginResult)
                .flatMap { x ->
                    korail.reserve(x.t2, x.t1)
                }
                .doOnSuccess {
                    alarmSender.sendSuccessMessage("anytimedebug@gmail.com")
                }
                .doOnError {
                    alarmSender.sendFailMessage("anytimedebug@gmail.com")
                }
                .block()
    }
}