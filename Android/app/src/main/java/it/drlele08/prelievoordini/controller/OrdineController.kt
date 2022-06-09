package it.drlele08.prelievoordini.controller

import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import it.drlele08.prelievoordini.Utilita
import it.drlele08.prelievoordini.model.ordine.Ordine
import it.drlele08.prelievoordini.model.ordine.ProdottoOrdine
import org.json.JSONObject
import java.util.ArrayList

class OrdineController
{
    fun getOrdini(idUtente:Int,token:String,pagina:Int,stato:Int,queue: RequestQueue,onSuccess:(ordini:ArrayList<Ordine>)->Unit,onError:(mess:String)->Unit)
    {
        val url="${Utilita.host}/api/ordine/seeOrdini?Token=${Utilita.token}&idUtente=${idUtente}&TokenAuth=${token}&Pagina=${pagina}&Stato=${stato}"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val ris=response.getInt("Ris")
                if(ris==1)
                {
                    val vett= ArrayList<Ordine>()
                    val ordini=response.getJSONArray("Vett")
                    for(i in 0 until ordini.length())
                    {
                        val item=ordini.getJSONObject(i)
                        val idOrdine=item.getInt("idOrdine")
                        val statoTmp=item.getString("Stato")
                        val nome=item.getString("Nome")
                        val note=item.getString("NoteExtra")
                        val data=item.getString("Data")
                        vett.add(Ordine(idOrdine,statoTmp,nome,note,data))
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
        jsonObjectRequest.retryPolicy =
            DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue.add(jsonObjectRequest)
    }

    fun getDetailOrdine(idUtente: Int,token: String,idOrdine:Int,queue: RequestQueue,onSuccess: (ordini: ArrayList<ProdottoOrdine>) -> Unit,onError: (mess: String) -> Unit)
    {
        val url="${Utilita.host}/api/ordine/detailOrdine?Token=${Utilita.token}&idUtente=${idUtente}&TokenAuth=${token}&idOrdine=${idOrdine}"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val ris=response.getInt("Ris")
                if(ris==1)
                {
                    val vett=ArrayList<ProdottoOrdine>()
                    val items=response.getJSONArray("Ordine")
                    for(i in 0 until items.length())
                    {
                        val item=items.getJSONObject(i)
                        val idRigaOrdine=item.getInt("idRigaOrdine")
                        val idArticolo=item.getInt("idArticolo")
                        val descrizione=item.getString("Descrizione")
                        val prezzoAcquisto=item.getDouble("PrezzoAcquisto").toFloat()
                        val prezzoAttuale=item.getDouble("PrezzoAttuale").toFloat()
                        val qntOrdine=item.getInt("QntOrdine")
                        val qntEvasa=item.getInt("QntEvasa")
                        vett.add(ProdottoOrdine(idRigaOrdine,idArticolo,descrizione,prezzoAcquisto,prezzoAttuale,qntOrdine,qntEvasa))
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
        jsonObjectRequest.retryPolicy =
            DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue.add(jsonObjectRequest)
    }

    fun doOrdine(idUtente: Int,token: String,note:String,queue: RequestQueue,onSuccess: () -> Unit,onError: (mess: String) -> Unit)
    {
        val url="${Utilita.host}/api/ordine/doOrdine"
        val json=JSONObject()
        json.put("Token",Utilita.token)
        json.put("idUtente",idUtente)
        json.put("TokenAuth",token)
        json.put("Note",note)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, json,
            { response ->
                val ris=response.getInt("Ris")
                if(ris==1)
                {
                    onSuccess()
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
        jsonObjectRequest.retryPolicy =
            DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue.add(jsonObjectRequest)
    }

    fun changeStatoOrdine(idUtente: Int,token: String,idOrdine: Int,newStato:Int,queue: RequestQueue,onSuccess: () -> Unit,onError: (mess: String) -> Unit)
    {
        val url="${Utilita.host}/api/ordine/statoOrdine"
        val json=JSONObject()
        json.put("Token",Utilita.token)
        json.put("idUtente",idUtente)
        json.put("TokenAuth",token)
        json.put("idOrdine",idOrdine)
        json.put("newStato",newStato)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, json,
            { response ->
                val ris=response.getInt("Ris")
                if(ris==1)
                {
                    onSuccess()
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
        jsonObjectRequest.retryPolicy =
            DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue.add(jsonObjectRequest)
    }
}