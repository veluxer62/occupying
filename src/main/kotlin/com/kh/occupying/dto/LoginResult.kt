package com.kh.occupying.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class LoginResult(
        @JsonProperty("Key")
        val key: String,
        @JsonProperty("strDiscCouponFlg")
        val strDiscCouponFlg: String,
        @JsonProperty("encryptMbCrdNo")
        val encryptMbCrdNo: String,
        @JsonProperty("encryptCustNo")
        val encryptCustNo: String,
        @JsonProperty("strPrsCnqeMsgCd")
        val strPrsCnqeMsgCd: String,
        @JsonProperty("strCustMgSrtCd")
        val strCustMgSrtCd: String,
        @JsonProperty("strDiscCrdReisuFlg")
        val strDiscCrdReisuFlg: String,
        @JsonProperty("strCustSrtCd")
        val strCustSrtCd: String,
        @JsonProperty("strGoffStnNm")
        val strGoffStnNm: String,
        @JsonProperty("strHdcpFlg")
        val strHdcpFlg: String,
        @JsonProperty("strBtdt")
        val strBtdt: String,
        @JsonProperty("h_msg_cd")
        val hMsgCd: String,
        @JsonProperty("strResult")
        val resultCode: String,
        @JsonProperty("strEvtTgtFlg")
        val strEvtTgtFlg: String,
        @JsonProperty("strEmailAdr")
        val email: String,
        @JsonProperty("strSexDvCd")
        val strSexDvCd: String,
        @JsonProperty("strCustLeadFlgNm")
        val strCustLeadFlgNm: String,
        @JsonProperty("h_msg_txt")
        val hMsgTxt: String,
        @JsonProperty("strAthnFlg")
        val strAthnFlg: String,
        @JsonProperty("strMbCrdNo")
        val MobileCredencial: String,
        @JsonProperty("strLognTpCd1")
        val strLognTpCd1: String,
        @JsonProperty("strCustLeadFlg")
        val strCustLeadFlg: String,
        @JsonProperty("strAthnFlg2")
        val strAthnFlg2: String,
        @JsonProperty("strSubtDcsClCd")
        val strSubtDcsClCd: String,
        @JsonProperty("strAbrdStnCd")
        val strAbrdStnCd: String,
        @JsonProperty("strCpNo")
        val strCpNo: String,
        @JsonProperty("strCustNo")
        val strCustNo: String,
        @JsonProperty("strCustDvCd")
        val strCustDvCd: String,
        @JsonProperty("strLognTpCd2")
        val strLognTpCd2: String,
        @JsonProperty("strCustNm")
        val userName: String,
        @JsonProperty("strCustClCd")
        val strCustClCd: String,
        @JsonProperty("strHdcpTpCd")
        val strHdcpTpCd: String,
        @JsonProperty("strYouthAgrFlg")
        val strYouthAgrFlg: String,
        @JsonProperty("strHdcpTpCdNm")
        val strHdcpTpCdNm: String,
        @JsonProperty("strAbrdStnNm")
        val strAbrdStnNm: String,
        @JsonProperty("strGoffStnCd")
        val strGoffStnCd: String
) : Serializable