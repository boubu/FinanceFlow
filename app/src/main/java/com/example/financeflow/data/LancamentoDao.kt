package com.example.financeflow.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LancamentoDao {
    @Insert
    suspend fun inserir(lancamento: Lancamento)

    @Query("SELECT * FROM lancamentos ORDER BY data DESC, id DESC")
    fun observarTodos(): LiveData<List<Lancamento>>

    @Query(
        """
        SELECT COALESCE(SUM(
            CASE WHEN tipo = 'RECEITA' THEN valor ELSE -valor END
        ), 0)
        FROM lancamentos
        """
    )
    fun observarSaldo(): LiveData<Double>
}
