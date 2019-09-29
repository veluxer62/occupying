package com.kh.occupying.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.kh.occupying.converter.LocalDateDeserializer
import com.kh.occupying.converter.LocalTimeDeserializer
import java.time.LocalDate
import java.time.LocalTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class Train(
        @JsonProperty("h_trn_seq")
        val sequence: String,
        @JsonProperty("h_chg_trn_dv_cd")
        val chgTrnDvCd: String,
        @JsonProperty("h_chg_trn_dv_nm")
        val chgTrnDvNm: String,
        @JsonProperty("h_chg_trn_seq")
        val chgTrnSeq: String,
        @JsonProperty("h_dpt_rs_stn_cd")
        val departureStationCode: String,
        @JsonProperty("h_dpt_rs_stn_nm")
        val departureStation: String,
        @JsonProperty("h_arv_rs_stn_cd")
        val destinationCode: String,
        @JsonProperty("h_arv_rs_stn_nm")
        val destination: String,
        @JsonProperty("h_trn_no")
        val trnNo: String,
        @JsonProperty("h_trn_no_qb")
        val trnNoQb: String,
        @JsonProperty("h_yms_apl_flg")
        val ymsAplFlg: String,
        @JsonProperty("h_trn_gp_cd")
        val trnGpCd: String,
        @JsonProperty("h_trn_clsf_cd")
        val trnClsfCd: String,
        @JsonProperty("h_run_dt")
        @JsonDeserialize(using = LocalDateDeserializer::class)
        val runDate: LocalDate,
        @JsonProperty("h_dpt_dt")
        @JsonDeserialize(using = LocalDateDeserializer::class)
        val departureDate: LocalDate,
        @JsonProperty("h_dpt_tm")
        @JsonDeserialize(using = LocalTimeDeserializer::class)
        val departureTime: LocalTime,
        @JsonProperty("h_dpt_tm_qb")
        val dptTmQb: String,
        @JsonProperty("h_arv_dt")
        @JsonDeserialize(using = LocalDateDeserializer::class)
        val arrivalDate: LocalDate,
        @JsonProperty("h_arv_tm")
        @JsonDeserialize(using = LocalTimeDeserializer::class)
        val arrivalTime: LocalTime,
        @JsonProperty("h_arv_tm_qb")
        val arvTmQb: String,
        @JsonProperty("h_expct_dlay_hr")
        val expctDlayHr: String,
        @JsonProperty("h_rsv_wait_ps_cnt")
        val rsvWaitPsCnt: String,
        @JsonProperty("h_dtour_flg")
        val dtourFlg: String,
        @JsonProperty("h_dtour_txt")
        val dtourTxt: String,
        @JsonProperty("h_std_rest_seat_cnt")
        val stdRestSeatCnt: String,
        @JsonProperty("h_fst_rest_seat_cnt")
        val fstRestSeatCnt: String,
        @JsonProperty("h_car_tp_cd")
        val carTpCd: String,
        @JsonProperty("h_car_tp_nm")
        val carTpNm: String,
        @JsonProperty("h_trn_cps_cd1")
        val trnCpsCd1: String,
        @JsonProperty("h_trn_cps_nm1")
        val trnCpsNm1: String,
        @JsonProperty("h_trn_cps_cd2")
        val trnCpsCd2: String,
        @JsonProperty("h_trn_cps_nm2")
        val trnCpsNm2: String,
        @JsonProperty("h_trn_cps_cd3")
        val trnCpsCd3: String,
        @JsonProperty("h_trn_cps_nm3")
        val trnCpsNm3: String,
        @JsonProperty("h_trn_cps_cd4")
        val trnCpsCd4: String,
        @JsonProperty("h_trn_cps_nm4")
        val trnCpsNm4: String,
        @JsonProperty("h_trn_cps_cd5")
        val trnCpsCd5: String,
        @JsonProperty("h_trn_cps_nm5")
        val trnCpsNm5: String,
        @JsonProperty("h_train_disc_rt")
        val trainDiscRt: String,
        @JsonProperty("h_wait_rsv_flg")
        val waitRsvFlg: String,
        @JsonProperty("h_rd_cnd_disc_no")
        val rdCndDiscNo: String,
        @JsonProperty("h_rd_cnd_disc_nm")
        val rdCndDiscNm: String,
        @JsonProperty("h_spe_rsv_cd")
        val speRsvCd: String,
        @JsonProperty("h_spe_rsv_cd2")
        val speRsvCd2: String?,
        @JsonProperty("h_spe_rsv_nm")
        val speRsvNm: String,
        @JsonProperty("h_spe_disc_rt")
        val speDiscRt: String,
        @JsonProperty("h_spe_seat_map_flg")
        val speSeatMapFlg: String,
        @JsonProperty("h_gen_rsv_cd")
        val genRsvCd: String,
        @JsonProperty("h_gen_rsv_cd2")
        val genRsvCd2: String?,
        @JsonProperty("h_gen_rsv_nm")
        val genRsvNm: String,
        @JsonProperty("h_gen_disc_rt")
        val genDiscRt: String,
        @JsonProperty("h_gen_seat_map_flg")
        val genSeatMapFlg: String,
        @JsonProperty("h_stnd_rsv_cd")
        val stndRsvCd: String,
        @JsonProperty("h_stnd_rsv_nm")
        val stndRsvNm: String,
        @JsonProperty("h_free_rsv_cd")
        val freeRsvCd: String,
        @JsonProperty("h_free_rsv_nm")
        val freeRsvNm: String,
        @JsonProperty("h_free_sracar_cnt")
        val freeSracarCnt: String,
        @JsonProperty("h_train_disc_gen_rt")
        val trainDiscGenRt: String,
        @JsonProperty("h_rd_add_info")
        val rdAddInfo: String,
        @JsonProperty("h_nonstop_msg")
        val nonstopMsg: String,
        @JsonProperty("h_nonstop_msg_txt")
        val nonstopMsgTxt: String,
        @JsonProperty("h_rd_seat_map_flg")
        val rdSeatMapFlg: String,
        @JsonProperty("h_dpt_stn_cons_ordr")
        val dptStnConsOrdr: String,
        @JsonProperty("h_arv_stn_cons_ordr")
        val arvStnConsOrdr: String,
        @JsonProperty("h_dpt_stn_run_ordr")
        val dptStnRunOrdr: String,
        @JsonProperty("h_arv_stn_run_ordr")
        val arvStnRunOrdr: String,
        @JsonProperty("h_seat_att_cd")
        val seatAttCd: String,
        @JsonProperty("h_rcvd_amt")
        val rcvdAmt: String,
        @JsonProperty("h_rcvd_fare")
        val rcvdFare: String,
        @JsonProperty("h_cnec_trfc_psb_flg")
        val cnecTrfcPsbFlg: String,
        @JsonProperty("h_cnec_trfc_nd_hm")
        val cnecTrfcNdHm: String,
        @JsonProperty("h_cnec_trfc_rcvd_prc")
        val cnecTrfcRcvdPrc: String,
        @JsonProperty("h_rsv_psb_flg")
        val canReservation: String,
        @JsonProperty("h_rsv_psb_nm")
        val reservationDisplay: String,
        @JsonProperty("h_stn_sale_flg")
        val stnSaleFlg: String,
        @JsonProperty("h_stn_sale_txt")
        val stnSaleTxt: String,
        @JsonProperty("h_info_txt")
        val infoTxt: String,
        @JsonProperty("h_trn_clsf_nm")
        val trainClass: String,
        @JsonProperty("h_trn_gp_nm")
        val trnGpNm: String
)