package br.grupointegrado.tads.clima

import android.app.Service
import android.net.Network
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import br.grupointegrado.tads.buscadorgithub.NetworkUtils
import br.grupointegrado.tads.clima.dados.ClimaPreferencias
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import org.w3c.dom.Text
import java.util.*

class MainActivity : AppCompatActivity() {

    /* val dadosClimaFicticios = listOf(
                  "Hoje - Céu limpo - 17°C / 15°C",
                 "Amanhã - Nublado - 19°C / 15°C",
                 "Quinta - Chuvoso - 30°C / 11°C",
                 "Sexta - Chuva com raios - 21°C / 9°C")*/

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
        if (item?.itemId == R.id.acao_refresh) {
          //  tv_dados_clima.text = ""
            carregarDadosDoClima()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun exibirResultado() {
        tv_dados_clima.visibility = View.VISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }


    fun exibirMensagemErro() {
        tv_dados_clima.visibility = View.INVISIBLE
        tv_mensagem_erro.visibility = View.VISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }

    fun exibirProgressBar() {
        tv_dados_clima.visibility = View.INVISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.VISIBLE
    }

    fun carregarDadosDoClima() {
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
                val url = NetworkUtils.construirUrl(localizacao)

                if (url != null) {
                    val resultado = NetworkUtils.obterRespostaDaUrlHttp(url)
                    return resultado
                }

            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            return null
        }


       /* override fun onPostExecute(result: String?) {

           if (result != null){
               tv_dados_clima.text = result
               exibirResultado()
           }else{
             exibirMensagemErro()
           }
          }*/


        override fun onPostExecute(resultado: String?) {
            if (resultado != null) {

                val json = JSONObject(resultado)
                val items = json.getJSONArray("list")

                for (i in 0 until items.length()) {
                    val obj = items.getJSONObject(i)
                    val atribDataRecibido = obj.getString("dt")
                    val dataHoraMilissegundos: Long = (java.lang.Long.valueOf(atribDataRecibido)) * 1000
                    val dataHora = Date(dataHoraMilissegundos)
                    val atribDataFormatada = DateFormat.format("dd/MM/yyyy ", dataHora)
                    val atribMain = obj.getJSONObject("main")
                    val atribTemp = atribMain.getString("temp")
                    val atribUmidade = atribMain.getString("humidity")

                    val atri_clima = obj.getJSONArray("weather")

                    val clima = atri_clima.getJSONObject(0)
                    val descricao = clima.getString("description")

                    tv_dados_clima.append(" Data -> $atribDataFormatada \n"
                            + " Temperatura -> $atribTemp \n"
                            + " Umidade -> $atribUmidade \n" +
                            " Clima-> $descricao \n\n\n")
                }
                exibirResultado()
            } else {
                exibirMensagemErro()
            }
        }
    }
}
