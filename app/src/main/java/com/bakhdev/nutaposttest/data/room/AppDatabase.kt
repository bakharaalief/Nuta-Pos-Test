package com.bakhdev.nutaposttest.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MoneyEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moneyDao(): MoneyDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "NutaposDB.db"
                ).build()
            }
    }
}