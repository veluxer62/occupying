package com.kh.occupying

interface TicketBox {
    fun <T> find(param: T): ItemDto<Ticket>
    fun reserve(ticket: Ticket)
}