package it.drlele08.prelievoordini.model.lettura

class InfoLettura(private val idOrdine:Int, private val nomeCliente:String, private val stato:String, private val data:String, private val orario:String, private val noteExtra:String, private val pzTotali:Int, private val pzEvasi:Int)
{
    fun getIdOrdine():Int
    {
        return idOrdine
    }

    fun getNomeCliente():String
    {
        return nomeCliente
    }

    fun getStato():String
    {
        return stato
    }

    fun getData():String
    {
        return data
    }

    fun getOrario():String
    {
        return orario
    }

    fun getNoteExtra():String
    {
        return noteExtra
    }

    fun getPzTotali():Int
    {
        return pzTotali
    }

    fun getPzEvasi():Int
    {
        return pzEvasi
    }
}