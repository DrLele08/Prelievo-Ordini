package it.drlele08.prelievoordini.controller

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import it.drlele08.prelievoordini.Utilita
import it.drlele08.prelievoordini.model.Utente
import org.json.JSONObject
import java.util.ArrayList

class UtenteController
{
    fun doLoginByEmail(email:String,pwd:String,queue:RequestQueue,onSuccess:(utente:Utente)->Unit,onError:(err:String)->Unit)
    {
        val url="${Utilita.host}/api/utente/loginByEmail?Token=${Utilita.token}&Email=${email}&Password=${pwd}"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val ris=response.getInt("Ris")
                if(ris==1)
                {
                    val obj=response.getJSONObject("Utente")
                    val idUtente=obj.getInt("idUtente")
                    val ksTipo=obj.getInt("ksTipo")
                    val nome=obj.getString("Nome")
                    val tokenAuth=obj.getString("tokenAuth")
                    val utente=Utente(idUtente,ksTipo,nome,email,tokenAuth)
                    onSuccess(utente)
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

    fun doLoginByToken(idUtente:Int,token:String,queue:RequestQueue,onSuccess:(utente:Utente)->Unit,onError:(err:String)->Unit)
    {
        val url="${Utilita.host}/api/utente/loginById?Token=${Utilita.token}&idUtente=${idUtente}&TokenAuth=${token}"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val ris=response.getInt("Ris")
                if(ris==1)
                {
                    val obj=response.getJSONObject("Utente")
                    val email=obj.getString("Email")
                    val ksTipo=obj.getInt("ksTipo")
                    val nome=obj.getString("Nome")
                    val tokenAuth=obj.getString("tokenAuth")
                    val utente=Utente(idUtente,ksTipo,nome,email,tokenAuth)
                    onSuccess(utente)
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

    fun getAllUtenti(idUtente: Int,token: String,queue: RequestQueue,onSuccess: (vettUtente: ArrayList<Utente>) -> Unit,onError: (err: String) -> Unit)
    {
        val url="${Utilita.host}/api/utente/utenti?Token=${Utilita.token}&idUtente=${idUtente}&TokenAuth=${token}"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val ris=response.getInt("Ris")
                if(ris==1)
                {
                    val vett=ArrayList<Utente>()
                    val users=response.getJSONArray("Vett")
                    for(i in 0 until users.length())
                    {
                        val item=users.getJSONObject(i)
                        val idTmp=item.getInt("idUtente")
                        val nome=item.getString("Nome")
                        val ruolo=item.getInt("Ruolo")
                        vett.add(Utente(idUtente=idTmp, tipoUtente =ruolo, nome = nome))
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

    fun getUtenteById(idUtente: Int,token:String,idUtenteFind:Int,queue: RequestQueue,onSuccess: (utente: Utente) -> Unit,onError: (err: String) -> Unit)
    {
        val url="${Utilita.host}/api/utente/utenteById?Token=${Utilita.token}&idUtente=${idUtente}&TokenAuth=${token}&Utente=${idUtenteFind}"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val ris=response.getInt("Ris")
                if(ris==1)
                {
                    val obj=response.getJSONObject("Utente")
                    val idNew=obj.getInt("idUtente")
                    val email=obj.getString("Email")
                    val ksTipo=obj.getInt("ksTipo")
                    val nome=obj.getString("Nome")
                    val ident=obj.getString("Identificativo")
                    val cell=obj.getString("Cellulare")
                    val utente=Utente(idNew,ksTipo,nome,email=email, identificativo = ident, cellulare = cell)
                    onSuccess(utente)
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

    fun doRegistrazione(nome:String,email:String,pwd: String,identificativo:String,cell:String,queue: RequestQueue,onSuccess: (utente:Utente) -> Unit,onError: (err: String) -> Unit)
    {
        if(nome.isNotEmpty() && email.isNotEmpty() && identificativo.isNotEmpty() && cell.isNotEmpty())
        {
            if(Utente.regexPwd.matches(pwd))
            {
                val json=JSONObject()
                json.put("Token",Utilita.token)
                json.put("Nome",nome)
                json.put("Email",email)
                json.put("Password",pwd)
                json.put("Identificativo",identificativo)
                json.put("Cellulare",cell)
                val url="${Utilita.host}/api/utente/signUp"
                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.POST, url, json,
                    { response ->
                        val ris=response.getInt("Ris")
                        if(ris==1)
                        {
                            val idObj=response.getJSONObject("Utente")
                            val idUtente=idObj.getInt("idUtente")
                            val tipo=idObj.getInt("ksTipo")
                            val tokenAuth=idObj.getString("tokenAuth")
                            onSuccess(Utente(idUtente,tipo,nome,email,tokenAuth,identificativo,cell))
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
                onError("La password non rispetta i requisiti")
            }
        }
        else
        {
            onError("Completa tutti i campi")
        }
    }
}
