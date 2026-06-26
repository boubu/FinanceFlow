package com.example.financeflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeflow.data.Lancamento
import com.example.financeflow.data.LancamentoRepository
import com.example.financeflow.data.TipoLancamento
import kotlinx.coroutines.launch

class LancamentoViewModel(private val repository: LancamentoRepository) : ViewModel() {

    sealed class ResultadoSalvar {
        object Sucesso : ResultadoSalvar()
        data class Erro(val mensagem: ErroValidacao) : ResultadoSalvar()
    }

    enum class ErroValidacao {
        VALOR_INVALIDO,
        DESCRICAO_VAZIA,
        DATA_NAO_SELECIONADA
    }

    fun salvarLancamento(
        valorTexto: String,
        descricao: String,
        dataEmMillis: Long?,
        tipo: TipoLancamento,
        aoFinalizar: (ResultadoSalvar) -> Unit
    ) {
        val valor = valorTexto.replace(",", ".").toDoubleOrNull()
        if (valor == null || valor <= 0.0) {
            aoFinalizar(ResultadoSalvar.Erro(ErroValidacao.VALOR_INVALIDO))
            return
        }

        if (descricao.isBlank()) {
            aoFinalizar(ResultadoSalvar.Erro(ErroValidacao.DESCRICAO_VAZIA))
            return
        }

        if (dataEmMillis == null) {
            aoFinalizar(ResultadoSalvar.Erro(ErroValidacao.DATA_NAO_SELECIONADA))
            return
        }

        val lancamento = Lancamento(
            valor = valor,
            descricao = descricao.trim(),
            data = dataEmMillis,
            tipo = tipo
        )

        viewModelScope.launch {
            repository.inserir(lancamento)
            aoFinalizar(ResultadoSalvar.Sucesso)
        }
    }
}
