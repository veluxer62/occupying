package com.kh.occupying

interface TicketBoxFactory {
    fun create(platform: TicketBoxPlatForm): TicketBox
}