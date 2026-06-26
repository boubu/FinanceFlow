package com.example.financeflow.data

import androidx.lifecycle.LiveData

class LancamentoRepository(private val dao: LancamentoDao) {
    val todosLancamentos: LiveData<List<Lancamento>> = dao.observarTodos()
    val saldoTotal: LiveData<Double> = dao.observarSaldo()

    suspend fun inserir(lancamento: Lancamento) {
        dao.inserir(lancamento)
    }
}
