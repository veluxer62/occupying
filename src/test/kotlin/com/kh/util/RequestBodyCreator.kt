package com.kh.util

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.kh.api.request.SearchTrainParams
import com.kh.api.request.SkillPayload
import com.kh.occupying.Korail
import com.kh.occupying.dto.response.SearchResponse

class RequestBodyCreator(private val korail: Korail) {

    fun reservationRequest(
            departureDate: String,
            departureTime: String,
            departureStation: String,
            destinationStation: String,
            id: String,
            pw: String,
            email: String
    ): String {
        val trainNo = getTrainNo(
                departureDate = departureDate,
                departureTime = departureTime,
                departureStation = departureStation,
                destinationStation = destinationStation
        )
        return """
                {
                    "intent": {
                    "id": "d7coyklcoly0q5lhjh6zdjzd",
                    "name": "블록 이름"
                  },
                  "userRequest": {
                    "timezone": "Asia/Seoul",
                    "params": {
                      "ignoreMe": "true"
                    },
                    "block": {
                      "id": "d7coyklcoly0q5lhjh6zdjzd",
                      "name": "블록 이름"
                    },
                    "utterance": "발화 내용",
                    "lang": null,
                    "user": {
                      "id": "515520",
                      "type": "accountId",
                      "properties": {}
                    }
                  },
                  "bot": {
                    "id": "5d97076fb617ea00012af4e0",
                    "name": "봇 이름"
                  },
                    "action": {
                        "name": "예약 API",
                        "clientExtra": {
                            "departure-time": "$departureTime",
                            "departure-date": "$departureDate",
                            "train-no": "$trainNo",
                            "destination-station": "$destinationStation",
                            "departure-station": "$departureStation"
                        },
                        "params": {
                            "id": "$id",
                            "pw": "$pw",
                            "email": "$email"
                        },
                        "id": "5d9dc807ffa7480001dace7e"
                    }
                }
            """.trimIndent()
    }

    private fun getTrainNo(
            departureDate: String,
            departureTime: String,
            departureStation: String,
            destinationStation: String
    ): String {
        val request = jacksonObjectMapper()
                .readValue(findTrainRequest(
                        departureDate = departureDate,
                        departureTime = departureTime,
                        departureStation = departureStation,
                        destinationStation = destinationStation
                ), SkillPayload::class.java)
        val payload = request.action.params
                .mapTo<SearchTrainParams>()
                .getSearchParams()
        return korail.search(payload).map {
            (it as SearchResponse).train.items
                    .map { response -> response.toDomain() }
                    .first { train -> train.hasSeat() }
                    .no
        }.block()!!
    }

    fun findTrainRequest(
            departureDate: String,
            departureTime: String,
            departureStation: String,
            destinationStation: String
    ): String {
        return """
                {
                  "intent": {
                    "id": "d7coyklcoly0q5lhjh6zdjzd",
                    "name": "블록 이름"
                  },
                  "userRequest": {
                    "timezone": "Asia/Seoul",
                    "params": {
                      "ignoreMe": "true"
                    },
                    "block": {
                      "id": "d7coyklcoly0q5lhjh6zdjzd",
                      "name": "블록 이름"
                    },
                    "utterance": "발화 내용",
                    "lang": null,
                    "user": {
                      "id": "515520",
                      "type": "accountId",
                      "properties": {}
                    }
                  },
                  "bot": {
                    "id": "5d97076fb617ea00012af4e0",
                    "name": "봇 이름"
                  },
                  "action": {
                    "name": "g0xxzoae15",
                    "clientExtra": null,
                    "params": {
                      "departure-date": "$departureDate",
                      "departure-time": "$departureTime",
                      "departure-station": "$departureStation",
                      "destination-station": "$destinationStation"
                    },
                    "id": "krcu052b90c6angense4fxgy"
                  }
                }
            """.trimIndent()
    }

}