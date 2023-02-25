package com.kh.deprecatedOccupying.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class ReservationResponse(
        @JsonProperty("h_msg_txt")
        override val responseMessage: String,
        @JsonProperty("h_msg_cd")
        override val responseCode: String,
        @JsonProperty("strResult")
        override val resultCode: ResultCode,
        @JsonProperty("h_seat_att_disc_flg")
        val seatAttDiscFlg: String,
        @JsonProperty("jrny_infos")
        val jrnyInfos: JrnyInfos,
        @JsonProperty("h_pay_cnt")
        val payCnt: String,
        @JsonProperty("h_jrny_cnt")
        val jrnyCnt: String,
        @JsonProperty("h_msg_txt3")
        val msgTxt3: String,
        @JsonProperty("h_lunchbox_chg_flg")
        val lunchboxChgFlg: String,
        @JsonProperty("h_pay_limit_msg")
        val payLimitMsg: String,
        @JsonProperty("h_msg_txt2")
        val msgTxt2: String,
        @JsonProperty("h_tmp_job_sqno1")
        val tmpJobSqno1: String,
        @JsonProperty("psgDiscAdd_infos")
        val psgDiscAddInfos: PsgDiscAddInfos,
        @JsonProperty("h_tot_rcvd_amt")
        val totRcvdAmt: String,
        @JsonProperty("h_dlay_apv_txt")
        val dlayApvTxt: String,
        @JsonProperty("h_guide")
        val guide: String,
        @JsonProperty("h_pre_stl_tgt_flg")
        val preStlTgtFlg: String,
        @JsonProperty("h_ntisu_lmt")
        val ntisuLmt: String,
        @JsonProperty("h_hdcp_ctfc_num")
        val hdcpCtfcNum: String,
        @JsonProperty("h_dlay_apv_flg")
        val dlayApvFlg: String,
        @JsonProperty("h_msg_txt4")
        val msgTxt4: String,
        @JsonProperty("h_disc_cnt")
        val discCnt: String,
        @JsonProperty("h_disc_crd_reisu_flg")
        val discCrdReisuFlg: String,
        @JsonProperty("h_ntisu_lmt_tm")
        val ntisuLmtTm: String,
        @JsonProperty("h_table_flg")
        val tableFlg: String,
        @JsonProperty("h_ntisu_lmt_dt")
        val ntisuLmtDt: String,
        @JsonProperty("h_tmp_job_sqno2")
        val tmpJobSqno2: String,
        @JsonProperty("h_psg_cnt")
        val psgCnt: String,
        @JsonProperty("h_add_srv_flg")
        val addSrvFlg: String,
        @JsonProperty("psg_infos")
        val psgInfos: PsgInfos,
        @JsonProperty("h_msg_mndry")
        val msgMndry: String,
        @JsonProperty("h_wct_no")
        val wctNo: String,
        @JsonProperty("h_pnr_no")
        val pnrNo: String,
        @JsonProperty("h_msg_txt5")
        val msgTxt5: String
) : CommonResponse
