package com.bakhdev.nutaposttest.domain.model

import java.math.BigDecimal

data class MoneyReport(
    val date: String = "",
    val sum: BigDecimal = BigDecimal("0")
)