package it.drlele08.prelievoordini.controller

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import it.drlele08.prelievoordini.Utilita
import it.drlele08.prelievoordini.model.ProdottoCarrello
import it.drlele08.prelievoordini.model.Utente
import org.json.JSONObject
import java.util.ArrayList

class CarrelloController
{
    fun showCart(idUtente:Int,token:String,queue: RequestQueue,onSuccess:(vett:ArrayList<ProdottoCarrello>)->Unit,onError:(mess:String)->Unit)
    {
        val url="${Utilita.host}/api/cart/seeCart?Token=${Utilita.token}&idUtente=${idUtente}&TokenAuth=${token}"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val ris=response.getInt("Ris")
                if(ris==1)
                {
                    val vett=response.getJSONArray("Cart")
                    val vettCart=ArrayList<ProdottoCarrello>()
                    for(i in 0 until vett.length())
                    {
                        val obj=vett.getJSONObject(i)
                        val idArt=obj.getInt("idArticolo")
                        val desc=obj.getString("Descrizione")
                        val prezzo=obj.getDouble("PrezzoIvato").toFloat()
                        val peso=obj.getInt("Peso")
                        val volume=obj.getInt("Volume")
                        val qntCart=obj.getInt("QntCart")
                        vettCart.add(ProdottoCarrello(idArt,desc,prezzo,peso,volume,qntCart))
                    }
                    onSuccess(vettCart)
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

    fun addToCart(idUtente: Int,token: String,idArticolo:Int,qnt:Int,queue: RequestQueue,onSuccess: () -> Unit,onError: (mess: String) -> Unit)
    {
        val url="${Utilita.host}/api/cart/itemCart"
        val json=JSONObject()
        json.put("Token",Utilita.token)
        json.put("idUtente",idUtente)
        json.put("TokenAuth",token)
        json.put("idArticolo",idArticolo)
        json.put("Qnt",qnt)
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
        queue.add(jsonObjectRequest)
    }

    fun removeToCart(idUtente: Int,token: String,idArticolo: Int,queue: RequestQueue,onSuccess: () -> Unit,onError: (mess: String) -> Unit)
    {
        val url="${Utilita.host}/api/cart/removeItemCart"
        val json=JSONObject()
        json.put("Token",Utilita.token)
        json.put("idUtente",idUtente)
        json.put("TokenAuth",token)
        json.put("idArticolo",idArticolo)
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
        queue.add(jsonObjectRequest)
    }

    fun clearCart(idUtente: Int,token: String,queue: RequestQueue,onSuccess: () -> Unit,onError: (mess: String) -> Unit)
    {
        val url="${Utilita.host}/api/cart/removeItemCart"
        val json=JSONObject()
        json.put("Token",Utilita.token)
        json.put("idUtente",idUtente)
        json.put("TokenAuth",token)
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
        queue.add(jsonObjectRequest)
    }
}