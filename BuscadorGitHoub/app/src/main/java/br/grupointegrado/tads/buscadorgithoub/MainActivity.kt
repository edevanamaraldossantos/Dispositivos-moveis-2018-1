package br.grupointegrado.tads.buscadorgithoub

import android.content.Context
import android.net.Network
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.net.URL

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
            //var resultado = NetworkUtils.obterRespostaDaUrlHttp(urlBuscaNoGithub)
            //tv_github_resultado.text = resultado

            val task = GithubBuscaTask();
            task.execute(urlBuscaNoGithub)
        }

    }

    inner class GithubBuscaTask : AsyncTask<URL, Void, String>() {

        override fun doInBackground(vararg params: URL?): String? {
            try {
                var url = params[0]
                var resultado = NetworkUtils.obterRespostaDaUrlHttp(url!!)
                return resultado
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            tv_github_resultado.text = result
        }
    }

}
