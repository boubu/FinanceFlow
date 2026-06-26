package com.example.financeflow.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.financeflow.data.Lancamento
import com.example.financeflow.data.LancamentoRepository

class ExtratoViewModel(repository: LancamentoRepository) : ViewModel() {
    val lancamentos: LiveData<List<Lancamento>> = repository.todosLancamentos
    val saldoTotal: LiveData<Double> = repository.saldoTotal
}
