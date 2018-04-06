package br.grupointegrado.tads.controledemetaspessoais

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //val dadosteste =  listOf("Edevan","Jessica","vanusa")
    var dados = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val listView = findViewById<ListView>(R.id.lv_metas)
        // listView.adapter = MyCustomAdpeter(this)

        val adaptar = ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, dados)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.opcao, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.acao_visualizar){
            visualizar()
        }else if(item?.itemId == R.id.acao_inserir){
            inserir()
        }else if(item?.itemId == R.id.acao_editar){
            editar()
        }else if(item?.itemId == R.id.acao_excluir){
            excluir()
        }
        return super.onOptionsItemSelected(item)

    }


    fun visualizar(){
        for ( dados in dados){
            if(dados != ""){
               // lv_metas.append("$dados \n")

            }else{
                Toast.makeText(this,"Não há Registro de metas Pessoais",Toast.LENGTH_LONG).show()
            }
        }
    }

    fun inserir(){
        val dat = edit_data.text.toString()
        val tit = edit_titulo.text.toString()
        val descri = edit_Descricao.text.toString()

        if (dat == "" ){
            Toast.makeText(this,"Informe a data Prevista",Toast.LENGTH_LONG).show()
        }else if (tit == ""){
            Toast.makeText(this,"Informe o Titulo de sua Meta",Toast.LENGTH_LONG).show()
        }else if (descri ==""){
            Toast.makeText(this,"Informe a Descrição de sua meta",Toast.LENGTH_LONG).show()
        }else{
          dados.add(dat+" "+tit+" "+descri)
        }

    }

    fun editar(){
        Toast.makeText(this,"Editar",Toast.LENGTH_SHORT).show()
    }

    fun excluir(){
        Toast.makeText(this,"Excluir",Toast.LENGTH_SHORT).show()
    }

    private class MyCustomAdpeter(context : Context) : BaseAdapter(){

        private val mcontext: Context

        init {
            this.mcontext = context
        }

        override fun getCount(): Int {
            return 1
        }

        override fun getItemId(position: Int): Long {
            return  position.toLong()
        }

        override fun getItem(position: Int): Any {
            return "Teste String"
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val textView = TextView(mcontext)
            textView.text = "Edevan e o cara"
            return  textView
        }

    }
}