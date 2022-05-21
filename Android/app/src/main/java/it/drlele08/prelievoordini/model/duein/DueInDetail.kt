package it.drlele08.prelievoordini.model.duein

class DueInDetail(private val idProdotto:Int,private val nome:String,private val qntArrivo:Int,private val prezzo:Float)
{
    fun getIdProdotto():Int
    {
        return idProdotto
    }

    fun getNome():String
    {
        return nome
    }

    fun getQntArrivo():Int
    {
        return qntArrivo
    }

    fun getPrezzo():Float
    {
        return prezzo
    }
}