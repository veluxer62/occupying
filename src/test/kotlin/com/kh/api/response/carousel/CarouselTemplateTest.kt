package com.kh.api.response.carousel

import com.kh.api.response.Buttons
import com.kh.api.response.buttonsExtra.ReserveExtra
import com.kh.api.response.listCard.ActionType
import com.kh.occupying.domain.Station
import com.kh.occupying.dto.response.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class CarouselTemplateTest {

    @Test
    fun `given SearchResponse fromResponse method will return template correctly`() {
        // Arrange
        val response = getSearchResponse()

        // Act
        val actual = CarouselTemplate.fromResponse(response)

        // Assert
        val baseCard = getBasicCard()

        assertThat(actual.carousel.type).isEqualTo(CarouselType.basicCard)
        assertThat(actual.carousel.items[0]).isEqualTo(baseCard)
    }

    private fun getBasicCard(): BasicCard {
        val reserveExtra = ReserveExtra(
                departureDate = LocalDate.of(2019, 10, 10),
                departureTime = LocalTime.of(7, 0, 0),
                trainNo = "233",
                departureStation = Station.서울,
                destinationStation = Station.부산
        )
        val button = Buttons(
                label = "예약하기",
                action = ActionType.block,
                messageText = "예약하기",
                blockId = "5d982f9d92690d0001a43950",
                extra = reserveExtra
        )
        return BasicCard(
                title = "[KTX]10월10일 [서울]07:00~[부산]09:30",
                description = "열차[233] 48800원 [예약 가능]",
                thumbnail = Thumbnail(
                        imageUrl = "https://t1.daumcdn.net/cfile/tistory/026E244F51CB94FA0C"
                ),
                buttons = listOf(
                        button
                )
        )
    }

    private fun getSearchResponse(): SearchResponse {
        val train = Train(
                sequence = UUID.randomUUID().toString(),
                chgTrnDvCd = UUID.randomUUID().toString(),
                chgTrnDvNm = UUID.randomUUID().toString(),
                chgTrnSeq = UUID.randomUUID().toString(),
                departureStationCode = "0001",
                departureStation = "서울",
                destinationCode = "0020",
                destination = "부산",
                trnNo = "233",
                trnNoQb = UUID.randomUUID().toString(),
                ymsAplFlg = UUID.randomUUID().toString(),
                trnGpCd = UUID.randomUUID().toString(),
                trainClassCode = "00",
                runDate = LocalDate.of(2019, 10, 10),
                departureDate = LocalDate.of(2019, 10, 10),
                departureTime = LocalTime.of(7, 0, 0),
                dptTmQb = UUID.randomUUID().toString(),
                arrivalDate = LocalDate.of(2019, 10, 10),
                arrivalTime = LocalTime.of(9, 30, 0),
                arvTmQb = UUID.randomUUID().toString(),
                expctDlayHr = UUID.randomUUID().toString(),
                rsvWaitPsCnt = UUID.randomUUID().toString(),
                dtourFlg = UUID.randomUUID().toString(),
                dtourTxt = UUID.randomUUID().toString(),
                stdRestSeatCnt = UUID.randomUUID().toString(),
                fstRestSeatCnt = UUID.randomUUID().toString(),
                carTpCd = UUID.randomUUID().toString(),
                carTpNm = UUID.randomUUID().toString(),
                trnCpsCd1 = UUID.randomUUID().toString(),
                trnCpsNm1 = UUID.randomUUID().toString(),
                trnCpsCd2 = UUID.randomUUID().toString(),
                trnCpsNm2 = UUID.randomUUID().toString(),
                trnCpsCd3 = UUID.randomUUID().toString(),
                trnCpsNm3 = UUID.randomUUID().toString(),
                trnCpsCd4 = UUID.randomUUID().toString(),
                trnCpsNm4 = UUID.randomUUID().toString(),
                trnCpsCd5 = UUID.randomUUID().toString(),
                trnCpsNm5 = UUID.randomUUID().toString(),
                trainDiscRt = UUID.randomUUID().toString(),
                waitRsvFlg = UUID.randomUUID().toString(),
                rdCndDiscNo = UUID.randomUUID().toString(),
                rdCndDiscNm = UUID.randomUUID().toString(),
                speRsvCd = UUID.randomUUID().toString(),
                speRsvCd2 = UUID.randomUUID().toString(),
                speRsvNm = UUID.randomUUID().toString(),
                speDiscRt = UUID.randomUUID().toString(),
                speSeatMapFlg = UUID.randomUUID().toString(),
                genRsvCd = "11",
                genRsvCd2 = UUID.randomUUID().toString(),
                genRsvNm = UUID.randomUUID().toString(),
                genDiscRt = UUID.randomUUID().toString(),
                genSeatMapFlg = UUID.randomUUID().toString(),
                stndRsvCd = UUID.randomUUID().toString(),
                stndRsvNm = UUID.randomUUID().toString(),
                freeRsvCd = UUID.randomUUID().toString(),
                freeRsvNm = UUID.randomUUID().toString(),
                freeSracarCnt = UUID.randomUUID().toString(),
                trainDiscGenRt = UUID.randomUUID().toString(),
                rdAddInfo = UUID.randomUUID().toString(),
                nonstopMsg = UUID.randomUUID().toString(),
                nonstopMsgTxt = UUID.randomUUID().toString(),
                rdSeatMapFlg = UUID.randomUUID().toString(),
                dptStnConsOrdr = UUID.randomUUID().toString(),
                arvStnConsOrdr = UUID.randomUUID().toString(),
                dptStnRunOrdr = UUID.randomUUID().toString(),
                arvStnRunOrdr = UUID.randomUUID().toString(),
                seatAttCd = UUID.randomUUID().toString(),
                rcvdAmt = "0000048800",
                rcvdFare = UUID.randomUUID().toString(),
                cnecTrfcPsbFlg = UUID.randomUUID().toString(),
                cnecTrfcNdHm = UUID.randomUUID().toString(),
                cnecTrfcRcvdPrc = UUID.randomUUID().toString(),
                canReservation = UUID.randomUUID().toString(),
                reservationDisplay = UUID.randomUUID().toString(),
                stnSaleFlg = UUID.randomUUID().toString(),
                stnSaleTxt = UUID.randomUUID().toString(),
                infoTxt = UUID.randomUUID().toString(),
                trainClassName = UUID.randomUUID().toString(),
                trnGpNm = UUID.randomUUID().toString()
        )
        val scheduledTrain = ScheduledTrain(
                items = listOf(
                        train
                )
        )
        return SearchResponse(
                responseMessage = UUID.randomUUID().toString(),
                responseCode = UUID.randomUUID().toString(),
                resultCode = ResultCode.SUCC,
                strJobId = UUID.randomUUID().toString(),
                menuId = UUID.randomUUID().toString(),
                gdNo = UUID.randomUUID().toString(),
                seatCntFirst = UUID.randomUUID().toString(),
                seatCntSecond = UUID.randomUUID().toString(),
                nextPgFlg = UUID.randomUUID().toString(),
                txtGoHourFirst = UUID.randomUUID().toString(),
                rsltCnt = Random().nextInt(),
                agreeTxt = UUID.randomUUID().toString(),
                train = scheduledTrain,
                noticeMsg = UUID.randomUUID().toString()
        )
    }

    @Test
    fun `given FailResponse fromResponse method will rturn template correctly`() {
        // Arrange
        val response = FailResponse(
                responseMessage = UUID.randomUUID().toString(),
                responseCode = UUID.randomUUID().toString(),
                resultCode = ResultCode.FAIL
        )

        // Act
        val actual = CarouselTemplate.fromResponse(response)

        // Assert
        assertThat(actual.carousel.type).isEqualTo(CarouselType.basicCard)
        assertThat(actual.carousel.items).isEmpty()
    }

}