package com.kh.occupying.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class PsgInfo(
        @JsonProperty("h_dcsp_no")
        val dcspNo: String,
        @JsonProperty("h_psg_tp_cd")
        val psgTpCd: String,
        @JsonProperty("h_dcnt_knd_cd2")
        val dcntKndCd2: String,
        @JsonProperty("h_dcnt_knd_cd")
        val dcntKndCd: String,
        @JsonProperty("h_dcsp_no2")
        val dcspNo2: String,
        @JsonProperty("h_psg_info_per_prnb")
        val psgInfoPerPrnb: String
)
