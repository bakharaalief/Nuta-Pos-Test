package com.bakhdev.nutaposttest.util

import com.bakhdev.nutaposttest.domain.model.Money
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


object DummyData {
    fun generateDummyData(): List<Money> {
        //dummy data
        val listData = ArrayList<Money>()
        val formatter = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
        for (i in 1..20) {
            listData.add(
                Money(
                    i,
                    (if (i % 2 == 0) formatter.parse("7-Jun-2013") else formatter.parse("8-Jun-2013")) as Date,
                    "to $i",
                    "from $i",
                    BigDecimal("1000000"),
                    "notes $i",
                    1,
                    "aaaaaaaaaaaa"
                )
            )
        }
        return listData
    }
}

