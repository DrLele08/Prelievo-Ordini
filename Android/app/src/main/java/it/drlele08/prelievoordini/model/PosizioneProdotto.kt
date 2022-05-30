package it.drlele08.prelievoordini.model

class PosizioneProdotto(private val idReparto:Int,private val nomeReparto:String,private val scaffale:Int,private val qnt:Int)
{
    fun getIdReparto():Int
    {
        return idReparto
    }

    fun getNomeReparto():String
    {
        return nomeReparto
    }

    fun getScaffale():Int
    {
        return scaffale
    }

    fun getQnt():Int
    {
        return qnt
    }
}