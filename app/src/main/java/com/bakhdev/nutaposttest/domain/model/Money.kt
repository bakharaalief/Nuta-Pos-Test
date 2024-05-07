package com.bakhdev.nutaposttest.domain.model

import java.math.BigDecimal
import java.util.Date

data class Money(
    val id: Int = 0,
    val date: Date = Date(),
    val to: String = "",
    val from: String = "",
    val amount: BigDecimal = BigDecimal(0),
    val notes: String = "",
    val type: Int = 0,
    val path: String = ""
)
