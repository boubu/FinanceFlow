package com.example.financeflow.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financeflow.FinanceFlowApplication
import com.example.financeflow.data.Lancamento
import com.example.financeflow.databinding.ActivityExtratoBinding
import com.example.financeflow.viewmodel.ExtratoViewModel
import com.example.financeflow.viewmodel.FinanceFlowViewModelFactory
import java.text.NumberFormat
import java.util.Locale

class ExtratoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExtratoBinding
    private lateinit var adapter: LancamentoAdapter

    private val viewModel: ExtratoViewModel by lazy {
        val app = application as FinanceFlowApplication
        ViewModelProvider(this, FinanceFlowViewModelFactory(app.repository))[ExtratoViewModel::class.java]
    }

    private val formatoMoeda = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExtratoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarRecyclerView()
        observarLancamentos()
        observarSaldo()
        configurarBotaoTrocarTema()
    }

    private fun configurarBotaoTrocarTema() {
        binding.fabThemeToggle.setOnClickListener {
            val modoAtual = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            if (modoAtual == Configuration.UI_MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    private fun configurarRecyclerView() {
        adapter = LancamentoAdapter()
        binding.recyclerExtrato.apply {
            layoutManager = LinearLayoutManager(this@ExtratoActivity)
            adapter = this@ExtratoActivity.adapter
        }
    }

    private fun observarLancamentos() {
        viewModel.lancamentos.observe(this) { lista: List<Lancamento> ->
            adapter.submitList(lista)
            val listaVazia = lista.isEmpty()
            binding.recyclerExtrato.visibility = if (listaVazia) View.GONE else View.VISIBLE
            binding.textListaVazia.visibility = if (listaVazia) View.VISIBLE else View.GONE
        }
    }

    private fun observarSaldo() {
        viewModel.saldoTotal.observe(this) { saldo: Double ->
            binding.textSaldoTotal.text = formatoMoeda.format(saldo)
        }
    }
}
