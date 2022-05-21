package it.drlele08.prelievoordini.model.duein

class DueIn(private val idDue:Int,private val dataArrivo:String,private val numProd:Int,private val descrizione:String)
{
    fun getIdDue():Int
    {
        return idDue
    }

    fun getDataArrivo():String
    {
        return dataArrivo
    }

    fun getNumProdotti():Int
    {
        return numProd
    }

    fun getDescrizione():String
    {
        return descrizione
    }
}