package br.grupointegrado.tads.listaverde

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView

class ListaVerdeAdapter : RecyclerView.Adapter<ListaVerdeAdapter.NumeroViewHolder> {

    var lista: List<Int>
    var context: Context
    var viewHolderCount = 0
    val itemClickListener: ItemClickListener

    constructor(context: Context, lista: List<Int>, itemClickListener: ItemClickListener) {
        this.context = context
        this.lista = lista
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumeroViewHolder {

        var mumeroListItem = LayoutInflater.from(context).inflate(R.layout.numero_list_item, parent, false)
        val numeroViewHolder = NumeroViewHolder(mumeroListItem)

        val tvViewHolderIndex = mumeroListItem.findViewById<TextView>(R.id.tv_viewhorder_index)
        tvViewHolderIndex.text = "ViewHolder $viewHolderCount"

        val cor = ColorUtils.getViewHolderBackgroundColor(context, viewHolderCount)
        mumeroListItem.setBackgroundColor(cor)

        viewHolderCount++
        return numeroViewHolder
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    interface ItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class NumeroViewHolder : RecyclerView.ViewHolder, View.OnClickListener {

        val tvItemNumero : TextView?

        constructor(itemView: View?) : super(itemView) {
            this.tvItemNumero = itemView?.findViewById(R.id.tv_item_numero)
            itemView?.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val posicaoClicada = adapterPosition
            itemClickListener.onItemClick(posicaoClicada)
        }
    }

    override fun onBindViewHolder(holder: NumeroViewHolder, position: Int) {
        val numero = lista.get(position)
        holder.tvItemNumero?.text = numero.toString()
    }
}