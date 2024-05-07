package com.bakhdev.nutaposttest.di

import android.content.Context
import com.bakhdev.nutaposttest.data.MoneyRepository
import com.bakhdev.nutaposttest.data.room.AppDatabase

object Injection {
    fun provideRepository(context: Context): MoneyRepository {
        val dao = AppDatabase.getInstance(context).moneyDao()
        return MoneyRepository.getInstance(dao)
    }
}