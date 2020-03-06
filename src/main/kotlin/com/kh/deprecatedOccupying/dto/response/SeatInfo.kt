package com.kh.deprecatedOccupying.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class SeatInfo(
        @JsonProperty("h_dcnt_reld_no")
        val dcntReldNo: String,
        @JsonProperty("h_etc_seat_att_cd")
        val etcSeatAttCd: String,
        @JsonProperty("h_smk_seat_att_cd")
        val smkSeatAttCd: String,
        @JsonProperty("h_srcar_no")
        val srcarNo: String,
        @JsonProperty("h_dir_seat_att_cd")
        val dirSeatAttCd: String,
        @JsonProperty("h_dcnt_knd_cd2")
        val dcntKndCd2: String,
        @JsonProperty("h_cert_no")
        val certNo: String,
        @JsonProperty("h_rcvd_amt")
        val rcvdAmt: String,
        @JsonProperty("h_seat_att_cd_2")
        val seatAttCd2: String,
        @JsonProperty("h_dcnt_knd_cd1_nm")
        val dcntKndCd1Nm: String,
        @JsonProperty("h_cert_dv_cd")
        val certDvCd: String,
        @JsonProperty("h_psrm_cl_cd")
        val psrmClCd: String,
        @JsonProperty("h_seat_no")
        val seatNo: String,
        @JsonProperty("h_dcnt_knd_cd2_nm")
        val dcntKndCd2Nm: String,
        @JsonProperty("h_rq_seat_att_cd")
        val rqSeatAttCd: String,
        @JsonProperty("h_sgr_nm")
        val sgrNm: String,
        @JsonProperty("h_dcnt_knd_cd1")
        val dcntKndCd1: String,
        @JsonProperty("h_seat_fare")
        val seatFare: String,
        @JsonProperty("h_loc_seat_att_cd")
        val locSeatAttCd: String,
        @JsonProperty("h_movie_psrm_flg")
        val moviePsrmFlg: String,
        @JsonProperty("h_disc_card_knd")
        val discCardKnd: String,
        @JsonProperty("h_seat_prc")
        val seatPrc: String,
        @JsonProperty("h_dcnt_knd_cd_nm1")
        val dcntKndCdNm1: String,
        @JsonProperty("h_cont_seat_cnt")
        val contSeatCnt: String,
        @JsonProperty("h_frbs_cd")
        val frbsCd: String,
        @JsonProperty("h_tot_disc_amt")
        val totDiscAmt: String,
        @JsonProperty("h_disc_card_re_cnt")
        val discCardReCnt: String,
        @JsonProperty("h_dcnt_knd_cd_nm2")
        val dcntKndCdNm2: String,
        @JsonProperty("h_psg_tp_cd")
        val psgTpCd: String,
        @JsonProperty("h_bkcls_cd")
        val bkclsCd: String,
        @JsonProperty("h_disc_card_use_cnt")
        val discCardUseCnt: String
)
