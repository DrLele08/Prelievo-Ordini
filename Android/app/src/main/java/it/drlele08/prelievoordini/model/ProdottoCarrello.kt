package it.drlele08.prelievoordini.model

class ProdottoCarrello(private val idArticolo:Int,private val descrizione:String,private val prezzoIvato:Float,private val pesoGrammi:Int,private val volume:Int,private val qntCart:Int)
{
    fun getIdArticolo():Int
    {
        return idArticolo
    }

    fun getDescrizione():String
    {
        return descrizione
    }

    fun getPrezzoIvato():Float
    {
        return prezzoIvato
    }

    fun getPesoGrammi():Int
    {
        return pesoGrammi
    }

    fun getVolume():Int
    {
        return volume
    }

    fun getQntCart():Int
    {
        return qntCart
    }
}