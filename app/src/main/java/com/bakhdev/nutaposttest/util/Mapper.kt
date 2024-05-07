package com.bakhdev.nutaposttest.util

import com.bakhdev.nutaposttest.data.room.MoneyEntity
import com.bakhdev.nutaposttest.domain.model.Money
import com.bakhdev.nutaposttest.domain.model.MoneyReport
import com.bakhdev.nutaposttest.ui.adapter.AdapterHeader
import com.bakhdev.nutaposttest.ui.adapter.AdapterItem
import com.bakhdev.nutaposttest.ui.adapter.AdapterValue

fun MoneyEntity.toMoney(): Money = Money(
    this.id,
    this.date,
    this.to,
    this.from,
    this.amount,
    this.notes,
    this.type,
    this.path
)

fun Money.toMoneyEntity(): MoneyEntity = MoneyEntity(
    this.id,
    this.date,
    this.to,
    this.from,
    this.amount,
    this.notes,
    this.type,
    this.path
)

fun List<MoneyEntity>.toListMoney(): List<Money> = this.map { it.toMoney() }

fun List<Money>.toAdapterItem(): List<AdapterItem> {
    val data = ArrayList<AdapterItem>()
    this.groupBy { it.date.toFormattedDate() }.map {
        data.add(
            AdapterHeader(
                MoneyReport(
                    it.key,
                    it.value.sumOf { money -> money.amount })
            )
        )
        it.value.reversed().forEach { money ->
            data.add(AdapterValue(money))
        }
    }
    return data
}

fun List<Money>.toAdapterItemLandscape(): List<AdapterItem> {
    val data = ArrayList<AdapterItem>()
    this.groupBy { it.date.toFormattedDate() }.map {
        it.value.reversed().forEach { money ->
            data.add(AdapterValue(money))
        }
        data.add(
            AdapterHeader(
                MoneyReport(
                    it.key,
                    it.value.sumOf { money -> money.amount })
            )
        )
    }
    return data
}

