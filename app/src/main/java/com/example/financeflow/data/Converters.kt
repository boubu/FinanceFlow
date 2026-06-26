package com.example.financeflow.data

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromTipoLancamento(tipo: TipoLancamento): String = tipo.name

    @TypeConverter
    fun toTipoLancamento(valor: String): TipoLancamento = TipoLancamento.valueOf(valor)
}
