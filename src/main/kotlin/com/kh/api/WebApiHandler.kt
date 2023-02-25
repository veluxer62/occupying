package com.kh.api

import com.kh.api.response.TrainDto
import com.kh.deprecatedOccupying.Korail
import com.kh.deprecatedOccupying.domain.Station
import com.kh.deprecatedOccupying.dto.param.SearchParams
import com.kh.deprecatedOccupying.dto.response.SearchResponse
import com.kh.occupying.ItemDto
import java.lang.Exception
import java.time.LocalDateTime
import java.util.NoSuchElementException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class WebApiHandler(
    private val korail: Korail
) {
    fun findTrains(serverRequest: ServerRequest): Mono<out ServerResponse> {
        return try {
            val departureDateTime = serverRequest.queryParam("departureDateTime").orElseThrow()
            val departureStation = serverRequest.queryParam("departureStation").orElseThrow()
            val destinationStation = serverRequest.queryParam("destinationStation").orElseThrow()

            val param = SearchParams(
                departureDatetime = LocalDateTime.parse(departureDateTime),
                departureStation = Station.valueOf(departureStation),
                destinationStation = Station.valueOf(destinationStation)
            )
            korail.search(param)
                .flatMap {
                    require(it is SearchResponse) {
                        it.responseMessage
                    }

                    ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(ItemDto(it.train.items.map { t -> TrainDto.from(t.toDomain()) }))
                }
        } catch (e1: NoSuchElementException) {
            ServerResponse.badRequest().build()
        } catch (e2: Exception) {
            ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(e2.message.orEmpty())
        }
    }

    fun reserveTrain(serverRequest: ServerRequest): Mono<out ServerResponse> {
        TODO("Not yet implemented")
    }

    fun retryTrainReservation(serverRequest: ServerRequest): Mono<out ServerResponse> {
        TODO("Not yet implemented")
    }
}
