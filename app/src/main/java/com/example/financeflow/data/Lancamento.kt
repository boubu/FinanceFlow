package com.example.financeflow.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lancamentos")
data class Lancamento(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val valor: Double,
    val descricao: String,
    val data: Long,
    val tipo: TipoLancamento
)

enum class TipoLancamento {
    RECEITA,
    DESPESA
}
