package com.bakhdev.nutaposttest.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bakhdev.nutaposttest.data.MoneyRepository
import com.bakhdev.nutaposttest.domain.model.Money
import kotlinx.coroutines.launch

class InputMoneyInViewModel(private val moneyRepository: MoneyRepository) : ViewModel() {

    fun insertMoney(money: Money) = viewModelScope.launch {
        moneyRepository.insertMoney(money)
    }
}