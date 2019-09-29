package com.kh.occupying

import com.fasterxml.jackson.databind.ObjectMapper
import com.kh.occupying.dto.FindTrainResult
import com.kh.occupying.dto.LoginResult
import org.springframework.web.reactive.function.client.WebClient

class Korail {

    fun login(id: String, pw: String): LoginResult {
        val request = WebClient
                .create("https://smart.letskorail.com/classes/com.korail.mobile")
                .post()
                .uri {
                    it.path(".login.Login")
                            .queryParam("Device", "AD")
                            .queryParam("txtInputFlg", "2")
                            .queryParam("txtMemberNo", id)
                            .queryParam("txtPwd", pw)
                            .build()
                }

        return request
                .retrieve()
                .bodyToMono(String::class.java)
                .block()
                .let {
                    ObjectMapper().readValue(it.orEmpty(),
                            LoginResult::class.java)
                }
    }

    fun search(departureAt: String,
               departureStation: String,
               destination: String): FindTrainResult {

        val departureDate = departureAt.substring(0, 8)
        val departureTime = departureAt.substring(8) + "0000"

        val request = WebClient
                .create("https://smart.letskorail.com/classes/com.korail.mobile")
                .post()
                .uri {
                    it.path(".seatMovie.ScheduleView")
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

        return request
                .retrieve()
                .bodyToMono(String::class.java)
                .block()
                .let {
                    ObjectMapper().readValue(it.orEmpty(),
                            FindTrainResult::class.java)
                }
    }
}