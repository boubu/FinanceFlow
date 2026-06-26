package com.example.financeflow.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.financeflow.R
import com.example.financeflow.data.Lancamento
import com.example.financeflow.data.TipoLancamento
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LancamentoAdapter : ListAdapter<Lancamento, LancamentoAdapter.LancamentoViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): LancamentoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lancamento, parent, false)
        return LancamentoViewHolder(view)
    }

    override fun onBindViewHolder(holder: LancamentoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class LancamentoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val frameIcone: FrameLayout = itemView.findViewById(R.id.frameIcone)
        private val imgIconeTipo: ImageView = itemView.findViewById(R.id.imgIconeTipo)
        private val textDescricao: TextView = itemView.findViewById(R.id.textDescricao)
        private val textData: TextView = itemView.findViewById(R.id.textData)
        private val textValor: TextView = itemView.findViewById(R.id.textValor)

        private val formatoMoeda = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        private val formatoData = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))

        fun bind(lancamento: Lancamento) {
            textDescricao.text = lancamento.descricao
            textData.text = formatoData.format(Date(lancamento.data))

            val context = itemView.context
            val ehReceita = lancamento.tipo == TipoLancamento.RECEITA

            val cor = if (ehReceita) {
                context.getColor(R.color.receita_color)
            } else {
                context.getColor(R.color.despesa_color)
            }

            val valorExibido = if (lancamento.valor < 0.01) 0.01 else lancamento.valor
            val sinal = if (ehReceita) "+ " else "- "
            textValor.text = sinal + formatoMoeda.format(valorExibido)
            textValor.setTextColor(cor)

            frameIcone.setBackgroundResource(
                if (ehReceita) R.drawable.bg_circulo_receita else R.drawable.bg_circulo_despesa
            )
            imgIconeTipo.setImageResource(
                if (ehReceita) R.drawable.ic_seta_receita else R.drawable.ic_seta_despesa
            )
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Lancamento>() {
        override fun areItemsTheSame(oldItem: Lancamento, newItem: Lancamento): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Lancamento, newItem: Lancamento): Boolean =
            oldItem == newItem
    }
}
