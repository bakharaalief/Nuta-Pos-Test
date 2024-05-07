package com.bakhdev.nutaposttest.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoneyDao {
    @Query(
        "SELECT * FROM money WHERE date(date / 1000,'unixepoch',  'localtime') " +
                "BETWEEN date(:startDate/1000,'unixepoch',  'localtime') " +
                "AND date(:startEnd/1000, 'unixepoch',  'localtime')"
    )
    fun getMoney(startDate: Long, startEnd: Long): LiveData<List<MoneyEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(moneyEntity: MoneyEntity)

    @Delete
    suspend fun delete(moneyEntity: MoneyEntity)
}