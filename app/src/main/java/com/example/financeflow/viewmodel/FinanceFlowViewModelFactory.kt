package com.example.financeflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.financeflow.data.LancamentoRepository

class FinanceFlowViewModelFactory(
    private val repository: LancamentoRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LancamentoViewModel::class.java) ->
                LancamentoViewModel(repository) as T

            modelClass.isAssignableFrom(ExtratoViewModel::class.java) ->
                ExtratoViewModel(repository) as T

            else -> throw IllegalArgumentException("ViewModel desconhecido: ${modelClass.name}")
        }
    }
}
