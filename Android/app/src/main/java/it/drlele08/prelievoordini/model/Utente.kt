package it.drlele08.prelievoordini.model

class Utente(private val idUtente:Int,private val tipoUtente:Int,private val nome:String,private val email:String="",private val tokenAuth:String ="",private val identificativo:String="",private val cellulare:String="")
{
    companion object
    {
        const val keyIdUtente="USER_ID"
        const val keyTokenUtente="USER_TOKEN"
        val regexPwd="""^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$""".toRegex()
    }
    fun getIdUtente():Int
    {
        return idUtente
    }

    fun getTipoUtente():Int
    {
        return tipoUtente
    }

    fun getNome():String
    {
        return nome
    }

    fun getIdentificativo():String
    {
        return identificativo
    }

    fun getCellulare():String
    {
        return cellulare
    }

    fun getEmail():String
    {
        return email
    }

    fun getTokenAuth():String
    {
        return tokenAuth
    }
}