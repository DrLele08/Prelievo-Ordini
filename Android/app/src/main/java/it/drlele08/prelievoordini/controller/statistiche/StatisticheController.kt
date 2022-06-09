package it.drlele08.prelievoordini.controller.statistiche

import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import it.drlele08.prelievoordini.Utilita
import org.json.JSONArray
import org.json.JSONObject

class StatisticheController
{
    fun getStatUtente(idUtente:Int,token:String,ksUtente:Int,queue: RequestQueue,onSuccess:(generiche:JSONArray,dettagli:JSONArray)->Unit,onError:(mess:String)->Unit)
    {
        val url="${Utilita.host}/api/stats/byId?Token=${Utilita.token}&idUtente=${idUtente}&TokenAuth=${token}&userStat=$ksUtente"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val ris=response.getInt("Ris")
                if(ris==1)
                {
                    onSuccess(response.getJSONArray("Stat"),response.getJSONArray("Dettagli"))
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

    fun getGenericStat(idUtente:Int,token: String,queue: RequestQueue,onSuccess:(mostLetture:JSONArray,mostError:JSONObject)->Unit,onError: (mess: String) -> Unit)
    {
        val url="${Utilita.host}/api/stats/generic?Token=${Utilita.token}&idUtente=${idUtente}&TokenAuth=${token}"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val ris=response.getInt("Ris")
                if(ris==1)
                {
                    onSuccess(response.getJSONArray("MostLetture"),response.getJSONObject("MostError"))
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