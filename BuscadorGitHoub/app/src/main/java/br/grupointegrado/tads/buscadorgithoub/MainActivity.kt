package br.grupointegrado.tads.buscadorgithoub

import android.content.Context
import android.net.Network
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_buscar){
            //Toast.makeText(this,"Clicou!", Toast.LENGTH_SHORT ).show()
            buscarNoGithub()
        }
        return super.onOptionsItemSelected(item)
    }

    fun buscarNoGithub(){
        var buscaGithub = et_busca.text.toString()
        var urlBuscaNoGithub = NetworkUtils.construirUrl(buscaGithub)
        tv_url.text = urlBuscaNoGithub.toString()

        if(urlBuscaNoGithub != null){
            var resultado = NetworkUtils.obterRespostaDaUrlHtpp(urlBuscaNoGithub)
            tv_github_resultado.text = resultado
        }

    }


}
