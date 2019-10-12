package com.kh.api.response.simpleText

import com.kh.occupying.dto.response.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.*
import java.util.stream.Stream

internal class SimpleTextTemplateTest {

    companion object {
        @JvmStatic
        fun init(): Stream<Arguments> {
            val randomString = UUID.randomUUID().toString()

            val loginResponse = ReservationResponse(
                    responseMessage = randomString,
                    responseCode = randomString,
                    resultCode = ResultCode.SUCC,
                    seatAttDiscFlg = randomString,
                    jrnyInfos = JrnyInfos(listOf()),
                    payCnt = randomString,
                    jrnyCnt = randomString,
                    msgTxt3 = randomString,
                    lunchboxChgFlg = randomString,
                    payLimitMsg = randomString,
                    msgTxt2 = randomString,
                    tmpJobSqno1 = randomString,
                    psgDiscAddInfos = PsgDiscAddInfos(listOf()),
                    totRcvdAmt = randomString,
                    dlayApvTxt = randomString,
                    guide = randomString,
                    preStlTgtFlg = randomString,
                    ntisuLmt = randomString,
                    hdcpCtfcNum = randomString,
                    dlayApvFlg = randomString,
                    msgTxt4 = randomString,
                    discCnt = randomString,
                    discCrdReisuFlg = randomString,
                    ntisuLmtTm = randomString,
                    tableFlg = randomString,
                    ntisuLmtDt = randomString,
                    tmpJobSqno2 = randomString,
                    psgCnt = randomString,
                    addSrvFlg = randomString,
                    psgInfos = PsgInfos(listOf()),
                    msgMndry = randomString,
                    wctNo = randomString,
                    pnrNo = randomString,
                    msgTxt5 = randomString,
                    acntApvNo = randomString
            )

            val failResponse = FailResponse(
                    resultCode = ResultCode.FAIL,
                    responseMessage = randomString,
                    responseCode = randomString
            )

            return Stream.of(
                    Arguments.of(loginResponse, "예약 성공하였습니다."),
                    Arguments.of(failResponse, "예약 실패하였습니다.")
            )
        }
    }

    @ParameterizedTest
    @MethodSource("init")
    fun `given CommonResponse fromResponse method will return SimpleText Correctly`(
            response: CommonResponse,
            expectedMessage: String
    ) {
        // Arrange & Act
        val actual = SimpleTextTemplate.fromResponse(response)

        // Assert
        assertThat(actual.simpleText.text).isEqualTo(expectedMessage)
    }

}