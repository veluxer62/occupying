package com.kh.occupying

import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import com.kh.occupying.dto.FindTrainResult
import com.kh.occupying.dto.LoginResult
import org.springframework.web.util.DefaultUriBuilderFactory
import org.springframework.web.util.UriBuilder
import org.springframework.web.util.UriBuilderFactory
import reactor.core.publisher.Mono
import java.net.URI

class Korail(private val client: WebClientWrapper) {

    fun login(id: String, pw: String): Mono<LoginResult> {
        val uri = makeUri(id, pw)
        return client.post(uri, jacksonTypeRef())
    }

    fun search(departureAt: String,
               departureStation: String,
               destination: String): Mono<FindTrainResult> {
        val uri = makeSearchUri(departureAt,
                destination, departureStation)
        return client.get(uri, jacksonTypeRef())
    }

    private fun makeUri(id: String, pw: String): (UriBuilder) -> URI {
        return {
            it.path("login.Login")
                    .queryParam("Device", "AD")
                    .queryParam("txtInputFlg", "2")
                    .queryParam("txtMemberNo", id)
                    .queryParam("txtPwd", pw)
                    .build()
        }
    }

    private fun makeSearchUri(departureAt: String,
                              destination: String,
                              departureStation: String): (UriBuilder) -> URI {
        val departureDate = departureAt.substring(0, 8)
        val departureTime = departureAt.substring(8) + "0000"

        return {
            it.path("seatMovie.ScheduleView")
                    .queryParam("Device", "AD")
                    .queryParam("Version", "190617001")
                    .queryParam("radJobId", "1")
                    .queryParam("selGoTrain", "00")
                    .queryParam("txtCardPsgCnt", "0")
                    .queryParam("txtGdNo", "")
                    .queryParam("txtGoAbrdDt", departureDate)
                    .queryParam("txtGoEnd", destination)
                    .queryParam("txtGoHour", departureTime)
                    .queryParam("txtGoStart", departureStation)
                    .queryParam("txtJobDv", "")
                    .queryParam("txtMenuId", "11")
                    .queryParam("txtPsgFlg_1", "1")
                    .queryParam("txtPsgFlg_2", "0")
                    .queryParam("txtPsgFlg_3", "0")
                    .queryParam("txtPsgFlg_4", "0")
                    .queryParam("txtPsgFlg_5", "0")
                    .queryParam("txtSeatAttCd_2", "000")
                    .queryParam("txtSeatAttCd_3", "000")
                    .queryParam("txtSeatAttCd_4", "015")
                    .queryParam("txtTrnGpCd", "")
                    .build()
        }
    }
}