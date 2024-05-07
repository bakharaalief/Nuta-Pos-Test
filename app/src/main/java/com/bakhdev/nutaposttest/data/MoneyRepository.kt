package com.bakhdev.nutaposttest.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.bakhdev.nutaposttest.data.room.MoneyDao
import com.bakhdev.nutaposttest.domain.model.Money
import com.bakhdev.nutaposttest.ui.adapter.AdapterItem
import com.bakhdev.nutaposttest.util.toAdapterItem
import com.bakhdev.nutaposttest.util.toAdapterItemLandscape
import com.bakhdev.nutaposttest.util.toListMoney
import com.bakhdev.nutaposttest.util.toMoneyEntity

class MoneyRepository private constructor(
    private val moneyDao: MoneyDao
) {
    fun getMoneyReport(dateStart: Long, dateEnd: Long): LiveData<List<AdapterItem>> =
        moneyDao.getMoney(dateStart, dateEnd).map { it.toListMoney().toAdapterItem() }

    fun getMoneyReportLandscape(dateStart: Long, dateEnd: Long): LiveData<List<AdapterItem>> =
        moneyDao.getMoney(dateStart, dateEnd).map { it.toListMoney().toAdapterItemLandscape() }

    suspend fun insertMoney(money: Money) {
        moneyDao.insert(money.toMoneyEntity())
    }

    suspend fun deleteMoney(money: Money) {
        moneyDao.delete(money.toMoneyEntity())
    }

    companion object {
        @Volatile
        private var instance: MoneyRepository? = null

        fun getInstance(
            moneyDao: MoneyDao,
        ): MoneyRepository =
            instance ?: synchronized(this) {
                instance ?: MoneyRepository(moneyDao)
            }.also { instance = it }
    }
}