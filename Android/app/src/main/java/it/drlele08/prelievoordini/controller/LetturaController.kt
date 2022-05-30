package it.drlele08.prelievoordini.controller

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import it.drlele08.prelievoordini.Utilita
import it.drlele08.prelievoordini.model.PosizioneProdotto
import it.drlele08.prelievoordini.model.lettura.DetailLettura
import it.drlele08.prelievoordini.model.lettura.InfoLettura
import it.drlele08.prelievoordini.model.lettura.Lettura
import it.drlele08.prelievoordini.model.lettura.LetturaProdotto
import org.json.JSONArray
import org.json.JSONObject

class LetturaController
{
    fun getLettureInevase(idUtente:Int, token:String, queue: RequestQueue, onSuccess:(letture:ArrayList<InfoLettura>)->Unit, onError:(mess:String)->Unit)
    {
        val url="${Utilita.host}/api/lettura/getLetture?Token=${Utilita.token}&idUtente=${idUtente}&TokenAuth=${token}"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val ris=response.getInt("Ris")
                if(ris==1)
                {
                    val vett=response.getJSONArray("Letture")
                    val lett=ArrayList<InfoLettura>()
                    for(i in 0 until vett.length())
                    {
                        val obj=vett.getJSONObject(i)
                        val idOrdine=obj.getInt("idOrdine")
                        val nomeCliente=obj.getString("Nome")
                        val stato=obj.getString("Stato")
                        val data=obj.getString("Data")
                        val orario=obj.getString("Orario")
                        val note=obj.getString("NoteExtra")
                        val pzTot=obj.getInt("PzTotali")
                        val pzEvasi=obj.getInt("PzEvasi")
                        lett.add(InfoLettura(idOrdine,nomeCliente,stato,data,orario,note,pzTot,pzEvasi))
                    }
                    onSuccess(lett)
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

    fun getLetturaInCorso(idUtente:Int, token:String, queue: RequestQueue, onSuccess:(letture:Lettura)->Unit, onError:(mess:String)->Unit)
    {
        val url="${Utilita.host}/api/lettura/letturaInCorso?Token=${Utilita.token}&idUtente=${idUtente}&TokenAuth=${token}"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val ris=response.getInt("Ris")
                if(ris==1)
                {
                    val idOperatore=response.getInt("idOperatore")
                    val prod=response.getJSONArray("Posizioni")
                    val vett=ArrayList<DetailLettura>()
                    for(i in 0 until prod.length())
                    {
                        val obj=prod.getJSONObject(i)
                        val idRiga=obj.getInt("idRigaOrdine")
                        val idArt=obj.getInt("idArticolo")
                        val desc=obj.getString("Descrizione")
                        val qntOrd=obj.getInt("QntOrdinata")
                        val vettPos=ArrayList<PosizioneProdotto>()
                        val vettTmp=obj.getJSONArray("Posizioni")
                        for(j in 0 until vettTmp.length())
                        {
                            val tmp=vettTmp.getJSONObject(j)
                            val idRep=tmp.getInt("idReparto")
                            val nomeRep=tmp.getString("NomeReparto")
                            val scaff=tmp.getInt("Scaffale")
                            val qnt=tmp.getInt("Qnt")
                            vettPos.add(PosizioneProdotto(idRep,nomeRep,scaff,qnt))
                        }
                        vett.add(DetailLettura(idRiga,idArt,desc,qntOrd,vettPos))
                    }
                    val lett=Lettura(idOperatore,vett)
                    onSuccess(lett)
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

    fun scegliLettura(idUtente: Int, token: String, idOrdine:Int, queue: RequestQueue, onSuccess: (lettura: Lettura) -> Unit, onError: (mess: String) -> Unit)
    {
        val url="${Utilita.host}/api/lettura/sceltaLettura"
        val json=JSONObject()
        json.put("Token",Utilita.token)
        json.put("idUtente",idUtente)
        json.put("TokenAuth",token)
        json.put("idOrdine",idOrdine)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, json,
            { response ->
                val ris=response.getInt("Ris")
                if(ris==1)
                {
                    val idOperatore=response.getInt("idOperatore")
                    val prod=response.getJSONArray("Prodotti")
                    val vett=ArrayList<DetailLettura>()
                    for(i in 0 until prod.length())
                    {
                        val obj=prod.getJSONObject(i)
                        val idRiga=obj.getInt("idRigaOrdine")
                        val idArt=obj.getInt("idArticolo")
                        val desc=obj.getString("Descrizione")
                        val qntOrd=obj.getInt("QntOrdinata")
                        val vettPos=ArrayList<PosizioneProdotto>()
                        val vettTmp=obj.getJSONArray("Posizioni")
                        for(j in 0 until vettTmp.length())
                        {
                            val tmp=vettTmp.getJSONObject(j)
                            val idRep=tmp.getInt("idReparto")
                            val nomeRep=tmp.getString("NomeReparto")
                            val scaff=tmp.getInt("Scaffale")
                            val qnt=tmp.getInt("Qnt")
                            vettPos.add(PosizioneProdotto(idRep,nomeRep,scaff,qnt))
                        }
                        vett.add(DetailLettura(idRiga,idArt,desc,qntOrd,vettPos))
                    }
                    val lett=Lettura(idOperatore,vett)
                    onSuccess(lett)
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

    fun updateLettura(idUtente: Int,token: String,idOperatoreLettura:Int,letture: ArrayList<LetturaProdotto>,queue: RequestQueue,onSuccess: () -> Unit,onError: (mess: String) -> Unit)
    {
        val vettJsonLett=JSONArray(letture)
        if(vettJsonLett.length()>0)
        {
            val url="${Utilita.host}/api/lettura/updateLettura"
            val json=JSONObject()
            json.put("Token",Utilita.token)
            json.put("idUtente",idUtente)
            json.put("TokenAuth",token)
            json.put("idOperatoreLettura",idOperatoreLettura)
            json.put("Letture",vettJsonLett)
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
        else
        {
            onError("Letture vuote")
        }
    }
}