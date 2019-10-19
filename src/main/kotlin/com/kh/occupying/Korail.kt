package com.kh.occupying

import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import com.kh.occupying.domain.Login
import com.kh.occupying.domain.Train
import com.kh.occupying.dto.param.SearchParams
import com.kh.occupying.dto.response.CommonResponse
import com.kh.occupying.dto.response.LoginResponse
import com.kh.occupying.dto.response.ReservationResponse
import com.kh.occupying.dto.response.SearchResponse
import com.kh.occupying.exception.TrainSeatIsNotAvailableException
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Mono
import java.net.URI
import java.time.Duration
import java.time.format.DateTimeFormatter

class Korail(private val client: WebClientWrapper) {

    fun login(id: String, pw: String): Mono<CommonResponse> {
        val uri = makeLoginUri(id, pw)
        return client
                .post(uri, jacksonTypeRef<LoginResponse>())
                .map {
                    if (it is LoginResponse)
                        it.copy(cookie = client.cookie)
                    else
                        it
                }
    }

    fun search(params: SearchParams): Mono<CommonResponse> {
        val uri = makeSearchUri(params)
        return client.get(uri, jacksonTypeRef<SearchResponse>())
    }

    fun findAvailableTrain(params: SearchParams,
                           trainNo: String,
                           retryCount: Long): Mono<Train> {
        return this.search(params)
                .map { response ->
                    val train = (response as SearchResponse).train.items
                            .map { item -> item.toDomain() }
                            .first { train ->
                                train.no == trainNo
                            }

                    if (!train.hasSeat())
                        throw TrainSeatIsNotAvailableException(
                                "예약가능한 좌석이 존재하지 않습니다.")

                    train
                }
                .delaySubscription(Duration.ofSeconds(1))
                .retry(retryCount) {
                    it is TrainSeatIsNotAvailableException
                }
    }

    fun reserve(login: Login, train: Train): Mono<CommonResponse> {
        val uri = makeReserveUri(train, login)
        return client.get(
                uri = uri,
                responseType = jacksonTypeRef<ReservationResponse>(),
                headers = login.generateHeaders()
        )
    }

    private fun makeReserveUri(train: Train, login: Login): (UriBuilder) -> URI {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val timeFormatter = DateTimeFormatter.ofPattern("HHmmss")
        return {
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
                    .queryParam("txtDptRsStnCd1", train.departureStation.code)
                    .queryParam("txtDptDt1",
                            train.departureDate.format(dateFormatter))
                    .queryParam("txtDptTm1",
                            train.departureTime.format(timeFormatter))
                    .queryParam("txtArvRsStnCd1", train.destinationStation.code)
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
    }

    private fun makeLoginUri(id: String, pw: String): (UriBuilder) -> URI {
        return {
            it.path("login.Login")
                    .queryParam("Device", "AD")
                    .queryParam("txtInputFlg", "2")
                    .queryParam("txtMemberNo", id)
                    .queryParam("txtPwd", pw)
                    .build()
        }
    }

    private fun makeSearchUri(params: SearchParams): (UriBuilder) -> URI {
        val departureDate = params.departureDatetime
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        val departureTime = params.departureDatetime
                .format(DateTimeFormatter.ofPattern("HHmmss"))

        return {
            it.path("seatMovie.ScheduleView")
                    .queryParam("Device", "AD")
                    .queryParam("Version", "190617001")
                    .queryParam("radJobId", "1")
                    .queryParam("selGoTrain", "00")
                    .queryParam("txtCardPsgCnt", "0")
                    .queryParam("txtGdNo", "")
                    .queryParam("txtGoAbrdDt", departureDate)
                    .queryParam("txtGoEnd", params.destinationStation)
                    .queryParam("txtGoHour", departureTime)
                    .queryParam("txtGoStart", params.departureStation)
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