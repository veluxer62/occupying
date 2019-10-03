package com.kh.occupying.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class JrnyInfo(
        @JsonProperty("h_arv_rs_stn_cd")
        val arvRsStnCd: String,
        @JsonProperty("h_trn_clsf_nm")
        val trnClsfNm: String,
        @JsonProperty("h_dpt_rs_stn_nm")
        val dptRsStnNm: String,
        @JsonProperty("h_jrny_tp_cd")
        val jrnyTpCd: String,
        @JsonProperty("h_trn_no")
        val trnNo: String,
        @JsonProperty("seat_infos")
        val seatInfos: SeatInfos,
        @JsonProperty("h_dpt_dt")
        val dptDt: String,
        @JsonProperty("h_tot_stnd_cnt")
        val totStndCnt: String,
        @JsonProperty("h_arv_tm")
        val arvTm: String,
        @JsonProperty("h_ob_flg")
        val obFlg: String,
        @JsonProperty("h_dpt_rs_stn_cd")
        val dptRsStnCd: String,
        @JsonProperty("h_dpt_tm")
        val dptTm: String,
        @JsonProperty("h_trn_clsf_cd")
        val trnClsfCd: String,
        @JsonProperty("h_tot_seat_cnt")
        val totSeatCnt: String,
        @JsonProperty("h_fres_cnt")
        val fresCnt: String,
        @JsonProperty("h_arv_rs_stn_nm")
        val arvRsStnNm: String,
        @JsonProperty("h_trn_gp_cd")
        val trnGpCd: String,
        @JsonProperty("h_seat_cnt")
        val seatCnt: String
)
