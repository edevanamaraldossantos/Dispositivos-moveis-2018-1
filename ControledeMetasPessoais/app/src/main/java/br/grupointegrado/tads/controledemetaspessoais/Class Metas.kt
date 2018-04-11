package br.grupointegrado.tads.controledemetaspessoais

import android.widget.ListAdapter

class Metas constructor(dataInicial : String, dataFinal: String, titulo: String, descricao : String, situacao : String) {

    var dataInicial : String
    var dataFinal : String
    var titulo : String
    var descricao : String
    var situacao : String

    init {
        this.dataInicial = dataInicial
        this.dataFinal = dataFinal
        this.titulo = titulo
        this.descricao = descricao
        this.situacao = situacao
    }

    override fun toString(): String {
        return "\nData de lançamento = $dataInicial \nData de Finalizada = $dataFinal\n" +
                "Titulo = $titulo\n Descricao = $descricao\n" +
                " Situção = $situacao\n"
    }

}
