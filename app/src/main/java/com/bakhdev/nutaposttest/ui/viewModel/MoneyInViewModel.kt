package com.bakhdev.nutaposttest.ui.viewModel

import android.content.res.Configuration
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bakhdev.nutaposttest.data.MoneyRepository
import com.bakhdev.nutaposttest.domain.model.Money
import com.bakhdev.nutaposttest.ui.adapter.AdapterItem
import kotlinx.coroutines.launch

class MoneyInViewModel(
    private val moneyRepository: MoneyRepository,
    private val state: SavedStateHandle
) : ViewModel() {

    fun listMoneyReport(
        dateStart: Long,
        dateEnd: Long,
        orientation: Int
    ): LiveData<List<AdapterItem>> = if (orientation == Configuration.ORIENTATION_LANDSCAPE)
        moneyRepository.getMoneyReportLandscape(dateStart, dateEnd)
    else moneyRepository.getMoneyReport(dateStart, dateEnd)

    fun delete(money: Money) = viewModelScope.launch {
        moneyRepository.deleteMoney(money)
    }

    fun saveStartDate(startDate: Long, endDate: Long) {
        state[START_DATE_KEY] = arrayOf<Long>(startDate, endDate)
    }

    fun getStartDate(): LiveData<Array<Long>> = state.getLiveData<Array<Long>>(START_DATE_KEY)

    companion object {
        private const val START_DATE_KEY = "START_DATE_KEY"
    }
}