package com.gouravsh.stackmarketapp.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

class IntradayInfo(
    val date: LocalDateTime,
    val close : Double,
) {
}