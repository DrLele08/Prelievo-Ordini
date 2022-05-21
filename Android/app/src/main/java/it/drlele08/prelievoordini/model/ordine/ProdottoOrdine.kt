package it.drlele08.prelievoordini.model.ordine

class ProdottoOrdine(private val idRigaOrdine:Int,private val idArticolo:Int,private val descrizione:String,private val prezzoAcquisto:Float,private val prezzoAttuale:Float,private val qntOrdine:Int,private val qntEvasa:Int)
{
    fun getIdRigaOrdine():Int
    {
        return idRigaOrdine
    }

    fun getIdArticolo():Int
    {
        return idArticolo
    }

    fun getDescrizione():String
    {
        return descrizione
    }

    fun getPrezzoAcquisto():Float
    {
        return prezzoAcquisto
    }

    fun getPrezzoAttuale():Float
    {
        return prezzoAttuale
    }

    fun getQntOrdine():Int
    {
        return qntOrdine
    }

    fun getQntEvasa():Int
    {
        return qntEvasa
    }
}