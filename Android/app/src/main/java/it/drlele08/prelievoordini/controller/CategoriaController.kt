package it.drlele08.prelievoordini.controller

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import it.drlele08.prelievoordini.Utilita
import it.drlele08.prelievoordini.model.Categoria
import it.drlele08.prelievoordini.model.Utente
import java.util.ArrayList

class CategoriaController
{
    fun getCategorie(queue: RequestQueue,onSuccess:(categorie:ArrayList<Categoria>)->Unit,onError:(mess:String)->Unit)
    {
        val url="${Utilita.host}/api/categoria/categorie?Token=${Utilita.token}"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val ris=response.getInt("Ris")
                if(ris==1)
                {
                    val vett= ArrayList<Categoria>()
                    val users=response.getJSONArray("Vett")
                    for(i in 0 until users.length())
                    {
                        val item=users.getJSONObject(i)
                        val idCat=item.getInt("idCategoria")
                        val nome=item.getString("Nome")
                        vett.add(Categoria(idCat,nome))
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