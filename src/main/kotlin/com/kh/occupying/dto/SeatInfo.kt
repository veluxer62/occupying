package com.kh.occupying.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class SeatInfo(
        @JsonProperty("h_dcnt_reld_no")
        val h_dcnt_reld_no: String,
        @JsonProperty("h_etc_seat_att_cd")
        val h_etc_seat_att_cd: String,
        @JsonProperty("h_smk_seat_att_cd")
        val h_smk_seat_att_cd: String,
        @JsonProperty("h_srcar_no")
        val h_srcar_no: String,
        @JsonProperty("h_dir_seat_att_cd")
        val h_dir_seat_att_cd: String,
        @JsonProperty("h_dcnt_knd_cd2")
        val h_dcnt_knd_cd2: String,
        @JsonProperty("h_cert_no")
        val h_cert_no: String,
        @JsonProperty("h_rcvd_amt")
        val h_rcvd_amt: String,
        @JsonProperty("h_seat_att_cd_2")
        val h_seat_att_cd_2: String,
        @JsonProperty("h_dcnt_knd_cd1_nm")
        val h_dcnt_knd_cd1_nm: String,
        @JsonProperty("h_cert_dv_cd")
        val h_cert_dv_cd: String,
        @JsonProperty("h_psrm_cl_cd")
        val h_psrm_cl_cd: String,
        @JsonProperty("h_seat_no")
        val h_seat_no: String,
        @JsonProperty("h_dcnt_knd_cd2_nm")
        val h_dcnt_knd_cd2_nm: String,
        @JsonProperty("h_rq_seat_att_cd")
        val h_rq_seat_att_cd: String,
        @JsonProperty("h_sgr_nm")
        val h_sgr_nm: String,
        @JsonProperty("h_dcnt_knd_cd1")
        val h_dcnt_knd_cd1: String,
        @JsonProperty("h_seat_fare")
        val h_seat_fare: String,
        @JsonProperty("h_loc_seat_att_cd")
        val h_loc_seat_att_cd: String,
        @JsonProperty("h_movie_psrm_flg")
        val h_movie_psrm_flg: String,
        @JsonProperty("h_disc_card_knd")
        val h_disc_card_knd: String,
        @JsonProperty("h_seat_prc")
        val h_seat_prc: String,
        @JsonProperty("h_dcnt_knd_cd_nm1")
        val h_dcnt_knd_cd_nm1: String,
        @JsonProperty("h_cont_seat_cnt")
        val h_cont_seat_cnt: String,
        @JsonProperty("h_frbs_cd")
        val h_frbs_cd: String,
        @JsonProperty("h_tot_disc_amt")
        val h_tot_disc_amt: String,
        @JsonProperty("h_disc_card_re_cnt")
        val h_disc_card_re_cnt: String,
        @JsonProperty("h_dcnt_knd_cd_nm2")
        val h_dcnt_knd_cd_nm2: String,
        @JsonProperty("h_psg_tp_cd")
        val h_psg_tp_cd: String,
        @JsonProperty("h_bkcls_cd")
        val h_bkcls_cd: String,
        @JsonProperty("h_disc_card_use_cnt")
        val h_disc_card_use_cnt: String
)
