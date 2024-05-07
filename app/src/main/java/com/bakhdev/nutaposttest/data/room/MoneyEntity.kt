package com.bakhdev.nutaposttest.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.math.BigDecimal
import java.util.Date

@Entity(tableName = "Money")
@TypeConverters(Converters::class)
data class MoneyEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("date") val date: Date = Date(),
    @ColumnInfo("to") val to: String = "",
    @ColumnInfo("from") val from: String = "",
    @ColumnInfo("amount") val amount: BigDecimal = BigDecimal(0),
    @ColumnInfo("notes") val notes: String = "",
    @ColumnInfo("type") val type: Int = 0,
    @ColumnInfo("path") val path: String = ""
)