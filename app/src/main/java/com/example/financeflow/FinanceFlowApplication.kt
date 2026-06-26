package com.example.financeflow

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.financeflow.data.FinanceFlowDatabase
import com.example.financeflow.data.LancamentoRepository

class FinanceFlowApplication : Application() {
    
    val repository: LancamentoRepository by lazy {
        val database = FinanceFlowDatabase.getDatabase(this)
        LancamentoRepository(database.lancamentoDao())
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }
}
