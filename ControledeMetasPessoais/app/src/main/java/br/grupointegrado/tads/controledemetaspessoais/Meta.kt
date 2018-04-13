package br.grupointegrado.tads.controledemetaspessoais

class Meta constructor(dataInicial : String, dataFinal: String, titulo: String, descricao : String, situacao : String) {

    var dataInicial : String
    var dataFinal : String
    var titulo : String
    var descricao : String
    var situacao : String

    init {
        this.dataInicial = dataInicial
        this.titulo = titulo
        this.descricao = descricao
        this.dataFinal = dataFinal
        this.situacao = situacao
    }

    override fun toString(): String {
        return "\nData de lançamento = $dataInicial \nData de Finalizada = $dataFinal\n"+
                "Titulo = $titulo\n Descricao = $descricao\n"+
                "Situação =$situacao\n"
    }

}
