package com.bakhdev.nutaposttest.util

import android.annotation.SuppressLint
import java.math.BigDecimal
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun String.toRupiahForm(): String {
    var data = this
    if (data.contains(".")) {
        data = data.replace(".", "")
    }
    val bigDecimal = BigDecimal(data)
    val symbols = DecimalFormatSymbols(Locale("in", "ID"))
    val formatter: NumberFormat = DecimalFormat("#,###", symbols)
    return formatter.format(bigDecimal)
}

fun BigDecimal.toRupiah(): String {
    val symbols = DecimalFormatSymbols(Locale("in", "ID"))
    val formatter: NumberFormat = DecimalFormat("#,###", symbols)
    return "Rp. ${formatter.format(this)}"
}

fun String.toDecimal(): String {
    var data = this
    if (data.contains(".")) {
        data = data.replace(".", "")
    }
    return data
}

@SuppressLint("SimpleDateFormat")
fun Date.toFormattedDate(): String {
    val dateFormat: DateFormat = SimpleDateFormat("dd MMM yyyy")
    return dateFormat.format(this)
}

@SuppressLint("SimpleDateFormat")
fun Date.toFormattedDateTime(): String {
    val dateFormat: DateFormat = SimpleDateFormat("HH:mm")
    return dateFormat.format(this)
}