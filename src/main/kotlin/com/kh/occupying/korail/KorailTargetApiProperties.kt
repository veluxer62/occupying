package com.kh.occupying.korail

import com.kh.occupying.TargetApiProperties

object KorailTargetApiProperties : TargetApiProperties {
    private const val schema = "https"
    private const val host = "smart.letskorail.com"
    private const val port = 443
    private const val contextPath = "/classes/com.korail.mobile."
    private const val findPath = "seatMovie.ScheduleView"
    private const val reservePath = "certification.TicketReservation"
    private const val loginPath = "login.Login"

    override fun getSchema() = schema
    override fun getHost() = host
    override fun getPort() = port
    override fun getFindPath() = contextPath + findPath
    override fun getReservePath() = contextPath + reservePath
    override fun getLoginPath() = contextPath + loginPath
}