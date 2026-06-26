package com.example.financeflow.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Lancamento::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FinanceFlowDatabase : RoomDatabase() {

    abstract fun lancamentoDao(): LancamentoDao

    companion object {
        @Volatile
        private var INSTANCE: FinanceFlowDatabase? = null

        fun getDatabase(context: Context): FinanceFlowDatabase {
            return INSTANCE ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    FinanceFlowDatabase::class.java,
                    "financeflow_database"
                ).build()
                INSTANCE = instancia
                instancia
            }
        }
    }
}
