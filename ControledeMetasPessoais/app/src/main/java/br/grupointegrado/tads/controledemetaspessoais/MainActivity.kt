package br.grupointegrado.tads.controledemetaspessoais

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var listV: ListView? = null
    private var dados = mutableListOf<Metas>()
    private var adaptar: ArrayAdapter<Metas>? = null
    private var posicao = -1

    var dataInicial: String = ""
    var dataFinal: String = ""
    var titulo: String = ""
    var descricao: String = ""
    var situacao: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val adaptar = ArrayAdapter<Metas>(this, android.R.layout.simple_list_item_1, dados)
        listV = findViewById<ListView>(R.id.lv_list_item)
        listV?.setAdapter(adaptar)

        listV?.setOnItemClickListener {parent, view, position, id ->
            posicao = position }

        listV?.setOnCreateContextMenuListener(View.OnCreateContextMenuListener {
            contextMenu, view, contextMenuInfo -> menuInflater.inflate(R.menu.menu_modificar, contextMenu) })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_inserir, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.acao_inserir) {
            inserir()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId == R.id.acao_editar) {
            retornarSelecionado()
        } else if (item?.itemId == R.id.acao_excluir) {
            excluir()
        } else if (item?.itemId == R.id.acao_finalizar) {
            Toast.makeText(this, "Finalizar", Toast.LENGTH_LONG).show()
        }
        return super.onContextItemSelected(item)
    }

    fun inserir() {
        getCampos()
        if (dataInicial == "") {
            Toast.makeText(this, "Informe a data Prevista", Toast.LENGTH_LONG).show()
        } else if (titulo == "") {
            Toast.makeText(this, "Informe o Titulo de sua Meta", Toast.LENGTH_LONG).show()
        } else if (descricao == "") {
            Toast.makeText(this, "Informe a Descrição de sua meta", Toast.LENGTH_LONG).show()
        } else if (dataInicial != "" && titulo != "" && descricao != "") {
            val meta = Metas(dataInicial.toString(), dataFinal.toString(),
                    titulo.toString(), descricao.toString(), situacao.toString())
            if (posicao == -1) {
                dados.add(meta)
            }else{
                dados[posicao] = meta
            }
            limparCampos()
            adaptar?.notifyDataSetChanged()
        }
    }

    private fun retornarSelecionado() {
        val atual = dados[posicao]
        edit_data.setText(atual.dataInicial)
        edit_titulo.setText(atual.titulo)
        edit_descricao.setText(atual.descricao)
    }

    fun getCampos() {
        dataInicial = edit_data.text.toString()
        titulo = edit_titulo.text.toString()
        descricao = edit_descricao.text.toString()
    }

    fun excluir() {
        dados.removeAt(posicao)
        limparCampos()
        adaptar?.notifyDataSetChanged()

        //implementar zerar index
    }

    fun finalizar() {
    }

    fun limparCampos() {
        edit_data.setText("")
        edit_titulo.setText("")
        edit_descricao.setText("")
    }
}
