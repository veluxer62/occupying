package com.kh.occupying

import reactor.core.publisher.Mono

interface TicketBox {
    fun <T> find(param: T): Mono<ItemDto<Ticket>>
    fun reserve(ticket: Ticket): Mono<Unit>
}