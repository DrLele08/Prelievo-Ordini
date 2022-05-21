package it.drlele08.prelievoordini.controller

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import it.drlele08.prelievoordini.Utilita
import it.drlele08.prelievoordini.model.duein.DueIn
import it.drlele08.prelievoordini.model.duein.DueInDetail
import java.util.ArrayList

class DueInController
{
    fun getDueIn(queue: RequestQueue,onSuccess:(vett:ArrayList<DueIn>)->Unit,onError:(mess:String)->Unit)
    {
        val url="${Utilita.host}/api/ordine/showDueIn?Token=${Utilita.token}"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val ris=response.getInt("Ris")
                if(ris==1)
                {
                    val vett=ArrayList<DueIn>()
                    val arr=response.getJSONArray("Vett")
                    for(i in 0 until arr.length())
                    {
                        val obj=arr.getJSONObject(i)
                        val idDue=obj.getInt("id")
                        val dataArrivo=obj.getString("dataArrivo")
                        val numProd=obj.getInt("numProd")
                        val desc=obj.getString("Descrizione")
                        vett.add(DueIn(idDue,dataArrivo,numProd,desc))
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

    fun getDetailDueIn(idDueIn:Int,queue:RequestQueue,onSuccess: (vett: ArrayList<DueInDetail>) -> Unit,onError: (mess: String) -> Unit)
    {
        val url="${Utilita.host}/api/ordine/detailDueIn?Token=${Utilita.token}&idDue=${idDueIn}"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val ris=response.getInt("Ris")
                if(ris==1)
                {
                    val vett=ArrayList<DueInDetail>()
                    val arr=response.getJSONArray("Vett")
                    for(i in 0 until arr.length())
                    {
                        val obj=arr.getJSONObject(i)
                        val idProdotto=obj.getInt("idProdotto")
                        val nome=obj.getString("nome")
                        val qnt=obj.getInt("qntArrivo")
                        val prezzo=obj.getDouble("prezzo").toFloat()
                        vett.add(DueInDetail(idProdotto,nome,qnt,prezzo))
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