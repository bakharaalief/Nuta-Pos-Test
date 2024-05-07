package com.bakhdev.nutaposttest.ui.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.bakhdev.nutaposttest.data.MoneyRepository
import com.bakhdev.nutaposttest.di.Injection

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor(
    private val moneyRepository: MoneyRepository,
) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return when {
            modelClass.isAssignableFrom(InputMoneyInViewModel::class.java) -> InputMoneyInViewModel(
                moneyRepository
            ) as T

            modelClass.isAssignableFrom(MoneyInViewModel::class.java) -> MoneyInViewModel(
                moneyRepository, extras.createSavedStateHandle()
            ) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideRepository(context)
                )
            }.also { instance = it }
    }
}