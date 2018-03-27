package br.grupointegrado.tads.buscadorgithoub

import android.content.Context
import android.net.Network
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.lang.Exception
import java.net.URL
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        exercicioJson()
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

    fun exibirResultado(){
        tv_github_resultado.visibility = View.VISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }


    fun exibirMensagemErro(){
        tv_github_resultado.visibility = View.INVISIBLE
        tv_mensagem_erro.visibility = View.VISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }

    fun  exibirProgessBar(){
        tv_github_resultado.visibility = View.INVISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.VISIBLE
    }

    fun  exercicioJson(){
        var dadosJson = """
       {
            "temperatura":{
            "minima": 11.34,
            "maxima": 19.01
       },
            "clima":{
            "id": 801,
            "condicao":"Nuvens",
            "descricao":"poucas nuvens"
       },
            "pressao":1023.51,
            "umidade":87
       }
       """

        val objetoPrevisao = JSONObject(dadosJson)
        val clima = objetoPrevisao.getJSONObject("clima")
        val condicao = clima.getString("condicao")
        val pressao = objetoPrevisao.getDouble("pressao")

        Log.d("exercicioJson","$condicao -> $pressao")
    }

    inner class GithubBuscaTask : AsyncTask<URL, Void, String>() {

        override fun onPreExecute() {
            exibirProgessBar()
        }

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
            if (result != null) {
                tv_github_resultado.text = result
                exibirResultado()
            }else{
             exibirMensagemErro()
            }
        }
    }

}
