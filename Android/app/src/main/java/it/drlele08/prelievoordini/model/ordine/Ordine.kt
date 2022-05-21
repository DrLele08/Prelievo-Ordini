package it.drlele08.prelievoordini.model.ordine

class Ordine(private val idOrdine:Int,private val stato:String,private val cliente:String,private val note:String,private val data:String)
{
    fun getIdOrdine():Int
    {
        return idOrdine
    }

    fun getStato():String
    {
        return stato
    }

    fun getCliente():String
    {
        return cliente
    }

    fun getNome():String
    {
        return note
    }

    fun getData():String
    {
        return data
    }
}