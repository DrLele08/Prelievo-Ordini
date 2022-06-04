package it.drlele08.prelievoordini.model

class UtenteMini(private val idUtente:Int,private val nome:String)
{
    fun getIdUtente():Int
    {
        return idUtente
    }

    fun getNome():String
    {
        return nome
    }
}