package br.grupointegrado.tads.clima

import android.net.Network
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import br.grupointegrado.tads.buscadorgithub.NetworkUtils
import br.grupointegrado.tads.clima.dados.ClimaPreferencias
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

        /* val dadosClimaFicticios = listOf("Hoje - Céu limpo - 17°C / 15°C",
                 "Amanhã - Nublado - 19°C / 15°C",
                 "Quinta - Chuvoso - 30°C / 11°C",
                 "Sexta - Chuva com raios - 21°C / 9°C",
                 "Sábado - Chuva com raios - 16°C / 7°C",
                 "Domingo - Chuvoso - 16°C / 8°C",
                 "Segunda - Parcialmente nublado - 15°C / 10°C",
                 "Ter, Mai 24 - Vai curintia - 16°C / 18°C",
                 "Qua, Mai 25 - Nublado - 19°C / 15°C",
                 "Qui, Mai 26 - Tempestade - 30°C / 11°C",
                 "Sex, Mai 27 - Furacão - 21°C / 9°C",
                 "Sáb, Mai 28 - Meteóro - 16°C / 7°C",
                 "Dom, Mai 29 - Apocalipse - 16°C / 8°C",
                 "Seg, Mai 30 - Pós-apocalipse - 15°C / 10°C")*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*   for (clima in dadosClimaFicticios){
            dados_clima.append("$clima \n\n\n")
        }

    */
        carregarDadosDoClima()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.clima, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.acao_refresh){
            tv_dados_clima.text =""
            carregarDadosDoClima()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun exibirResultado(){
        tv_dados_clima.visibility = View.VISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }

    fun exibirMensagemErro(){
        tv_dados_clima.visibility = View.INVISIBLE
        tv_mensagem_erro.visibility = View.VISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }

    fun exibirProgressBar(){
        tv_dados_clima.visibility = View.INVISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.VISIBLE
    }

    fun carregarDadosDoClima( ){
        val localizacao = ClimaPreferencias.getLocalizacaoSalva(this)
        buscarClimaTask().execute(localizacao)
    }

    inner class buscarClimaTask : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            exibirProgressBar()
        }

        override fun doInBackground(vararg params: String): String? {

            try {
                val localizacao = params[0]
                val  url = NetworkUtils.construirUrl(localizacao)

                if (url != null) {
                    val resultado = NetworkUtils.obterRespostaDaUrlHttp(url)
                    return  resultado
                }

            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: String?) {

            if (result != null){
                tv_dados_clima.text = result
                exibirResultado()
            }else{
              exibirMensagemErro()
            }
        }
    }

}
