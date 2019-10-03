package com.kh.occupying.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class SearchResponse(
        @JsonProperty("h_msg_txt")
        override val responseMessage: String,
        @JsonProperty("h_msg_cd")
        override val responseCode: String,
        @JsonProperty("strResult")
        override val resultCode: String,
        @JsonProperty("strJobId")
        val strJobId: String,
        @JsonProperty("h_menu_id")
        val menuId: String,
        @JsonProperty("h_gd_no")
        val gdNo: String,
        @JsonProperty("h_seat_cnt_first")
        val seatCntFirst: String,
        @JsonProperty("h_seat_cnt_second")
        val seatCntSecond: String,
        @JsonProperty("h_next_pg_flg")
        val nextPgFlg: String,
        @JsonProperty("txtGoHour_first")
        val txtGoHourFirst: String,
        @JsonProperty("h_rslt_cnt")
        val rsltCnt: String,
        @JsonProperty("h_agree_txt")
        val agreeTxt: String,
        @JsonProperty("trn_infos")
        val train: ScheduledTrain,
        @JsonProperty("h_notice_msg")
        val noticeMsg: String
) : CommonResponse