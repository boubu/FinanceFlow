package com.example.financeflow.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.financeflow.FinanceFlowApplication
import com.example.financeflow.R
import com.example.financeflow.data.TipoLancamento
import com.example.financeflow.databinding.ActivityLancamentoBinding
import com.example.financeflow.viewmodel.FinanceFlowViewModelFactory
import com.example.financeflow.viewmodel.LancamentoViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class LancamentoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLancamentoBinding

    private val viewModel: LancamentoViewModel by lazy {
        val app = application as FinanceFlowApplication
        ViewModelProvider(this, FinanceFlowViewModelFactory(app.repository))[LancamentoViewModel::class.java]
    }

    private var dataSelecionadaMillis: Long? = null
    private val formatoExibicaoData = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLancamentoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarSeletorDeData()
        configurarBotaoSalvar()
        configurarBotaoVerExtrato()
    }

    private fun configurarSeletorDeData() {
        binding.textData.setOnClickListener {
            val calendario = Calendar.getInstance()
            dataSelecionadaMillis?.let { calendario.timeInMillis = it }

            DatePickerDialog(
                this,
                { _, ano, mes, dia ->
                    val selecionado = Calendar.getInstance()
                    selecionado.set(ano, mes, dia, 0, 0, 0)
                    selecionado.set(Calendar.MILLISECOND, 0)
                    dataSelecionadaMillis = selecionado.timeInMillis
                    binding.textData.text = formatoExibicaoData.format(selecionado.time)
                },
                calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun configurarBotaoSalvar() {
        binding.btnSalvar.setOnClickListener {
            val tipo = if (binding.radioReceita.isChecked) {
                TipoLancamento.RECEITA
            } else {
                TipoLancamento.DESPESA
            }

            viewModel.salvarLancamento(
                valorTexto = binding.editValor.text.toString(),
                descricao = binding.editDescricao.text.toString(),
                dataEmMillis = dataSelecionadaMillis,
                tipo = tipo
            ) { resultado ->
                when (resultado) {
                    is LancamentoViewModel.ResultadoSalvar.Sucesso -> {
                        Toast.makeText(
                            this,
                            getString(R.string.msg_lancamento_salvo),
                            Toast.LENGTH_SHORT
                        ).show()
                        limparFormulario()
                    }
                    is LancamentoViewModel.ResultadoSalvar.Erro -> {
                        exibirErroDeValidacao(resultado.mensagem)
                    }
                }
            }
        }
    }

    private fun exibirErroDeValidacao(erro: LancamentoViewModel.ErroValidacao) {
        val mensagem = when (erro) {
            LancamentoViewModel.ErroValidacao.VALOR_INVALIDO ->
                getString(R.string.erro_valor_obrigatorio)
            LancamentoViewModel.ErroValidacao.DESCRICAO_VAZIA ->
                getString(R.string.erro_descricao_obrigatoria)
            LancamentoViewModel.ErroValidacao.DATA_NAO_SELECIONADA ->
                getString(R.string.erro_data_obrigatoria)
        }
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
    }

    private fun limparFormulario() {
        binding.editValor.text.clear()
        binding.editDescricao.text.clear()
        binding.textData.text = getString(R.string.hint_data)
        binding.radioReceita.isChecked = true
        dataSelecionadaMillis = null
    }

    private fun configurarBotaoVerExtrato() {
        binding.btnVerExtrato.setOnClickListener {
            startActivity(Intent(this, ExtratoActivity::class.java))
        }
    }
}
