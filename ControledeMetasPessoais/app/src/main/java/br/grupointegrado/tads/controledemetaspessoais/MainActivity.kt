package br.grupointegrado.tads.controledemetaspessoais

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    var listV: ListView? = null
    private var dados = mutableListOf<Meta>()
    private var posicao = -1
    private var pos = -1
    var dataInicial: String = ""
    var dataFinal: String = ""
    var titulo: String = ""
    var descricao: String = ""
    var situacao: String = "A"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listV = findViewById(R.id.lv_list_item)

        listV?.setOnItemClickListener { parent, view, position, id ->
            pos = position
            situacaoMeta()
        }

        listV?.setOnCreateContextMenuListener(View.OnCreateContextMenuListener { contextMenu, view, contextMenuInfo -> menuInflater.inflate(R.menu.menu_modificar, contextMenu) })
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

        val menuList = item?.getMenuInfo() as AdapterView.AdapterContextMenuInfo
        posicao = menuList.position

        if (item?.itemId == R.id.acao_editar) {
            retornarSelecionado()
        } else if (item?.itemId == R.id.acao_excluir) {
            excluir()
        } else if (item?.itemId == R.id.acao_finalizar) {
            finalizar()
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

            val meta = Meta(dataInicial.toString(), dataFinal.toString(),
                    titulo.toString(), descricao.toString(), situacao.toString())

            var verificarData = validarData(dataInicial)

            if (verificarData != null) {
                if (posicao == -1) {
                    dados.add(meta)
                    //Toast.makeText(this, "adiciou!!!", Toast.LENGTH_LONG).show()
                } else {
                    dados[posicao] = meta
                    //Toast.makeText(this, " alterou!!!", Toast.LENGTH_LONG).show()
                }
            }
            atualizarCampos()
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
        edit_data.requestFocus()
    }

    fun excluir() {
        dados.removeAt(posicao)
        limparCampos()
        atualizarCampos()
        //Toast.makeText(this, "Removeu!!!", Toast.LENGTH_LONG).show()
    }

    fun finalizar() {
        val data = pegarData()
        dataFinal = pegarData().toString()
        situacao = "F"
        retornarSelecionado()
        inserir()
        atualizarCampos()
        Toast.makeText(this, "Finalizou!!!", Toast.LENGTH_LONG).show()
    }

    fun limparCampos() {
        edit_data.setText("")
        edit_titulo.setText("")
        edit_descricao.setText("")
        situacao = "A"
        dataFinal = ""
    }

    fun atualizarCampos() {
        val adaptar = ArrayAdapter<Meta>(this, android.R.layout.simple_list_item_1, dados)
        listV?.setAdapter(adaptar)
        adaptar?.notifyDataSetChanged()
        posicao = -1
        limparCampos()
    }

    fun pegarData(): CharSequence {
        val data = Date()
        val dataFormatada = android.text.format.DateFormat.format("dd/MM/yyyy ", data)
        return dataFormatada
    }

    fun validarData(data: String): Date? {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        sdf.setLenient(false)
        var dataAgora: Date = Date()
        var dt = dataAgora

        try {
            dataAgora = sdf.parse(data)
            if (vericarData(data)) {
                return dataAgora
            } else {
                Toast.makeText(this, "Verifique os dados -> Dia/Mês/Ano!!!", Toast.LENGTH_SHORT).show();
                return null
            }
        } catch (ex: Exception) {
            Toast.makeText(this, " Não é Data!!!", Toast.LENGTH_SHORT).show();
            return null
        }
    }

    fun vericarData(data: String): Boolean {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        var dt = Date()
        dt = sdf.parse(data)
        val dataAgora = Calendar.getInstance().time
        var tpDia = (24 * 60 * 60 * 1000)
        val difDia = ((dt!!.time - dataAgora.time) / tpDia)

        if (difDia < 0) {
            return false
        } else {
            return true
        }
    }

    fun situacaoMeta() {
        val atual = dados[pos]
        var sit = atual.situacao
        var tpDia = (24 * 60 * 60 * 1000)
        var dt = validarData(atual.dataInicial)
        val dataAgora = Calendar.getInstance().time
        var difDia = (((dt!!.time - dataAgora.time) / tpDia) + 1)
        pos = -1

        if (sit != "F") {
            if (difDia < 0) {
                Toast.makeText(this, "Meta não comprida!!!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "falta (" + difDia + ") Dia para o Encerramento!!!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Meta Finalizada!!!", Toast.LENGTH_SHORT).show();
        }
    }
}


