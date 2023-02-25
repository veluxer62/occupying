package com.kh.service

import com.kh.api.request.ReservationParams
import com.kh.deprecatedOccupying.Korail
import com.kh.deprecatedOccupying.dto.param.SearchParams
import com.kh.deprecatedOccupying.dto.response.LoginResponse
import org.springframework.scheduling.annotation.Async
import reactor.core.publisher.Mono

open class BackgroundExecutor(
        private val korail: Korail,
        private val alarmSender: AlarmSender
) {
    @Async
    open fun reserveTrain(searchPayload: SearchParams,
                          trainNo: String,
                          reservationPayload: ReservationParams) {
        val findTrains = korail.findAvailableTrain(
                params = searchPayload,
                trainNo = trainNo,
                retryCount = 1800
        )

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
            e.printStackTrace()
            alarmSender.sendFailMessage(reservationPayload.email)
        }
    }
}
