package com.kh.occupying.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class FindTrainResult(
        @JsonProperty("h_msg_cd")
        val h_msg_cd: String,
        @JsonProperty("h_msg_txt")
        val h_msg_txt: String,
        @JsonProperty("strJobId")
        val strJobId: String,
        @JsonProperty("h_menu_id")
        val h_menu_id: String,
        @JsonProperty("h_gd_no")
        val h_gd_no: String,
        @JsonProperty("h_seat_cnt_first")
        val h_seat_cnt_first: String,
        @JsonProperty("h_seat_cnt_second")
        val h_seat_cnt_second: String,
        @JsonProperty("h_next_pg_flg")
        val h_next_pg_flg: String,
        @JsonProperty("txtGoHour_first")
        val txtGoHour_first: String,
        @JsonProperty("h_rslt_cnt")
        val h_rslt_cnt: String,
        @JsonProperty("h_agree_txt")
        val h_agree_txt: String,
        @JsonProperty("trn_infos")
        val train: ScheduledTrain,
        @JsonProperty("h_notice_msg")
        val h_notice_msg: String,
        @JsonProperty("strResult")
        val resultCode: String
)