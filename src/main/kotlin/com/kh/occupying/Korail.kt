package com.kh.occupying

import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import com.kh.occupying.domain.Login
import com.kh.occupying.domain.Train
import com.kh.occupying.dto.FindTrainResult
import com.kh.occupying.dto.response.LoginResponse
import com.kh.occupying.dto.ReservationResult
import com.kh.occupying.dto.response.CommonResponse
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Mono
import java.net.URI
import java.time.format.DateTimeFormatter

class Korail(private val client: WebClientWrapper) {

    fun login(id: String, pw: String): Mono<CommonResponse> {
        val uri = makeUri(id, pw)
        return client.post(uri, jacksonTypeRef<LoginResponse>())
    }

    fun search(departureAt: String,
               departureStation: String,
               destination: String): Mono<FindTrainResult> {
        val uri = makeSearchUri(departureAt,
                destination, departureStation)
        return client.get(uri, jacksonTypeRef())
    }

    fun reserve(login: Login, train: Train): Mono<ReservationResult> {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val timeFormatter = DateTimeFormatter.ofPattern("HHmmss")
        val uri: (UriBuilder) -> URI = {
            it.path("certification.TicketReservation")
                    .queryParam("Device", "AD")
                    .queryParam("txtSeatAttCd1", "000") // 좌석 속성
                    .queryParam("txtSeatAttCd2", "000")
                    .queryParam("txtSeatAttCd3", "000")
                    .queryParam("txtSeatAttCd4", "015")
                    .queryParam("txtSeatAttCd5", "000")
                    .queryParam("txtStndFlg", "N") // 입석여부
                    .queryParam("txtJrnyCnt", "1") // 여정수
                    .queryParam("txtJrnySqno1", "001") // 여정일련번호
                    .queryParam("txtJrnyTpCd1", "11") // 여정유형코드
                    .queryParam("txtTotPsgCnt", train.passenger.headCount)
                    .queryParam("txtDptRsStnCd1", train.departureStationCode)
                    .queryParam("txtDptDt1",
                            train.departureDate.format(dateFormatter))
                    .queryParam("txtDptTm1",
                            train.departureTime.format(timeFormatter))
                    .queryParam("txtArvRsStnCd1", train.destinationCode)
                    .queryParam("txtTrnNo1", train.no)
                    .queryParam("txtTrnClsfCd1", train.trainClass)
                    .queryParam("txtPsrmClCd1", train.seatClass)
                    .queryParam("txtTrnGpCd1", train.trainGroup)
                    .queryParam("txtPsgTpCd1", train.passenger.type)
                    .queryParam("txtDiscKndCd1",
                            train.passenger.discount.type)
                    .queryParam("txtCompaCnt1",
                            train.passenger.headCount)
                    .queryParam("txtCardCode_1",
                            train.passenger.discount.code)
                    .queryParam("txtCardNo_1",
                            train.passenger.discount.no)
                    .queryParam("txtCardPw_1",
                            train.passenger.discount.password)
                    .queryParam("key", login.key)

                    .build()
        }
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