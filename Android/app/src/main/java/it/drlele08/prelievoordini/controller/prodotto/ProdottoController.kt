package it.drlele08.prelievoordini.controller.prodotto

import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import it.drlele08.prelievoordini.Utilita
import it.drlele08.prelievoordini.model.Prodotto
import java.util.ArrayList

class ProdottoController
{
    fun getProdotti(idUtente:Int=-1,tokenAuth:String="",pagina:Int,filtro:Int,descrizione:String,categoria:Int,queue: RequestQueue,onSuccess:(prodotti:ArrayList<Prodotto>)->Unit,onError:(mess:String)->Unit)
    {
        val url="${Utilita.host}/api/prodotto/prodotti?Token=${Utilita.token}&idUtente=${idUtente}&TokenAuth=${tokenAuth}&Pagina=${pagina}&Filtro=${filtro}&Descrizione=${descrizione}&Categoria=${categoria}"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val ris=response.getInt("Ris")
                if(ris==1)
                {
                    val vett= ArrayList<Prodotto>()
                    val prod=response.getJSONArray("Prodotti")
                    for(i in 0 until prod.length())
                    {
                        val item=prod.getJSONObject(i)
                        val idArt=item.getInt("idArticolo")
                        val desc=item.getString("Descrizione")
                        val qnt=item.getInt("QntDisponibile")

                        var prezzo=0.0F
                        var prezzoCons=0.0F
                        var lung=0
                        var alt=0
                        var prof=0
                        var vol=0
                        var pesoG=0
                        if(idUtente != -1)
                        {
                            prezzo=item.getDouble("PrezzoIvato").toFloat()
                            prezzoCons=item.getDouble("PrezzoConsigliato").toFloat()
                            lung=item.getInt("Lunghezza")
                            alt=item.getInt("Altezza")
                            prof=item.getInt("Profondita")
                            vol=item.getInt("Volume")
                            pesoG=item.getInt("Peso")
                        }
                        vett.add(Prodotto(idArt,desc,qnt,prezzo,prezzoCons,lung,alt,prof,vol,pesoG))
                    }
                    onSuccess(vett)
                }
                else
                {
                    val mess=response.getString("Mess")
                    onError(mess)
                }
            },
            { error ->
                onError(error.toString())
            }
        )
        queue.add(jsonObjectRequest)
    }

    fun getProdottoByEan(idUtente:Int=-1,tokenAuth:String="",ean:String,queue: RequestQueue,onSuccess:(prodotto:Prodotto,qntEan:Int)->Unit,onError:(mess:String)->Unit)
    {
        val url="${Utilita.host}/api/prodotto/prodottoByEan?Token=${Utilita.token}&idUtente=${idUtente}&TokenAuth=${tokenAuth}&EAN=${ean}"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val ris=response.getInt("Ris")
                if(ris==1)
                {
                    val item=response.getJSONObject("Prodotto")
                    val idArt=item.getInt("idArticolo")
                    val desc=item.getString("Descrizione")
                    val qnt=item.getInt("QntDisponibile")
                    val prezzo=item.getDouble("PrezzoIvato").toFloat()
                    val prezzoCons=item.getDouble("PrezzoConsigliato").toFloat()
                    val lung=item.getInt("Lunghezza")
                    val alt=item.getInt("Altezza")
                    val prof=item.getInt("Profondita")
                    val vol=item.getInt("Volume")
                    val pesoG=item.getInt("Peso")
                    val qntEan=response.getInt("QntEan")
                    onSuccess(Prodotto(idArt,desc,qnt,prezzo,prezzoCons,lung,alt,prof,vol,pesoG),qntEan)
                }
                else
                {
                    val mess=response.getString("Mess")
                    onError(mess)
                }
            },
            { error ->
                onError(error.toString())
            }
        )
        queue.add(jsonObjectRequest)
    }

    fun getProdottoById(idUtente:Int=-1,tokenAuth:String="",idProdotto:Int,queue: RequestQueue,onSuccess:(prodotto:Prodotto)->Unit,onError:(mess:String)->Unit)
    {
        val url="${Utilita.host}/api/prodotto/prodottoById?Token=${Utilita.token}&idUtente=${idUtente}&TokenAuth=${tokenAuth}&idProdotto=${idProdotto}"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val ris=response.getInt("Ris")
                if(ris==1)
                {
                    val item=response.getJSONObject("Prodotto")
                    val idArt=item.getInt("idArticolo")
                    val desc=item.getString("Descrizione")
                    val qnt=item.getInt("QntDisponibile")
                    val prezzo=item.getDouble("PrezzoIvato").toFloat()
                    val prezzoCons=item.getDouble("PrezzoConsigliato").toFloat()
                    val lung=item.getInt("Lunghezza")
                    val alt=item.getInt("Altezza")
                    val prof=item.getInt("Profondita")
                    val vol=item.getInt("Volume")
                    val pesoG=item.getInt("Peso")
                    onSuccess(Prodotto(idArt,desc,qnt,prezzo,prezzoCons,lung,alt,prof,vol,pesoG))
                }
                else
                {
                    val mess=response.getString("Mess")
                    onError(mess)
                }
            },
            { error ->
                onError(error.toString())
            }
        )
        queue.add(jsonObjectRequest)
    }

    fun getProdottiByTags(idUtente:Int=-1,tokenAuth:String="",tags:String,queue: RequestQueue,onSuccess:(prodotto:ArrayList<Prodotto>)->Unit,onError:(mess:String)->Unit)
    {
        val url="${Utilita.host}/api/prodotto/prodottoByTags?Token=${Utilita.token}&idUtente=${idUtente}&TokenAuth=${tokenAuth}&Tags=${tags}"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val ris=response.getInt("Ris")
                if(ris==1)
                {
                    val arr=response.getJSONArray("Prodotti")
                    val vett=ArrayList<Prodotto>()
                    for(i in 0 until arr.length())
                    {
                        val item=arr.getJSONObject(i)
                        val idArt=item.getInt("idArticolo")
                        val desc=item.getString("Descrizione")
                        val qnt=item.getInt("QntDisponibile")
                        val prezzo=item.getDouble("PrezzoIvato").toFloat()
                        val prezzoCons=item.getDouble("PrezzoConsigliato").toFloat()
                        val lung=item.getInt("Lunghezza")
                        val alt=item.getInt("Altezza")
                        val prof=item.getInt("Profondita")
                        val vol=item.getInt("Volume")
                        val pesoG=item.getInt("Peso")
                        vett.add(Prodotto(idArt,desc,qnt,prezzo,prezzoCons,lung,alt,prof,vol,pesoG))
                    }
                    onSuccess(vett)
                }
                else
                {
                    val mess=response.getString("Mess")
                    onError(mess)
                }
            },
            { error ->
                onError(error.toString())
            }
        )
        queue.add(jsonObjectRequest)
    }

    fun getPreOrderProdotti(idUtente:Int=-1,tokenAuth:String="",pagina:Int,filtro:Int,descrizione:String,categoria:Int,queue: RequestQueue,onSuccess:(prodotti:ArrayList<Prodotto>)->Unit,onError:(mess:String)->Unit)
    {
        val url="${Utilita.host}/api/prodotto/preOrder?Token=${Utilita.token}&idUtente=${idUtente}&TokenAuth=${tokenAuth}&Pagina=${pagina}&Filtro=${filtro}&Descrizione=${descrizione}&Categoria=${categoria}"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val ris=response.getInt("Ris")
                if(ris==1)
                {
                    val vett= ArrayList<Prodotto>()
                    val prod=response.getJSONArray("Prodotti")
                    for(i in 0 until prod.length())
                    {
                        val item=prod.getJSONObject(i)
                        val idArt=item.getInt("idArticolo")
                        val desc=item.getString("Descrizione")
                        val qnt=item.getInt("QntDisponibile")
                        val prezzo=item.getDouble("PrezzoPreOrder").toFloat()
                        val prezzoCons=item.getDouble("PrezzoConsigliato").toFloat()
                        val lung=item.getInt("Lunghezza")
                        val alt=item.getInt("Altezza")
                        val prof=item.getInt("Profondita")
                        val vol=item.getInt("Volume")
                        val pesoG=item.getInt("Peso")
                        vett.add(Prodotto(idArt,desc,qnt,prezzo,prezzoCons,lung,alt,prof,vol,pesoG))
                    }
                    onSuccess(vett)
                }
                else
                {
                    val mess=response.getString("Mess")
                    onError(mess)
                }
            },
            { error ->
                onError(error.toString())
            }
        )
        queue.add(jsonObjectRequest)
    }
}