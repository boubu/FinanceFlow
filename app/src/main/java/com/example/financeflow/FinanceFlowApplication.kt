package com.example.financeflow

import android.app.Application
import com.example.financeflow.data.FinanceFlowDatabase
import com.example.financeflow.data.LancamentoRepository

class FinanceFlowApplication : Application() {
    val repository: LancamentoRepository by lazy {
        val database = FinanceFlowDatabase.getDatabase(this)
        LancamentoRepository(database.lancamentoDao())
    }
}
