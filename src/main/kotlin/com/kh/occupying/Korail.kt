package com.kh.occupying

import com.fasterxml.jackson.databind.ObjectMapper
import com.kh.occupying.dto.LoginResult
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

class Korail {
    fun login(id: String, pw: String): LoginResult {
        val request = WebClient
                .create("https://smart.letskorail.com")
                .post()
                .uri {
                    it.path("/classes/com.korail.mobile.login.Login")
                            .queryParam("Device", "AD")
                            .queryParam("txtInputFlg", "2")
                            .queryParam("txtMemberNo", id)
                            .queryParam("txtPwd", pw)
                            .build()
                }

        return request
                .retrieve()
                .bodyToMono(String::class.java)
                .block()
                .let {
                    ObjectMapper().readValue(it.orEmpty(), LoginResult::class.java)
                }
    }
}