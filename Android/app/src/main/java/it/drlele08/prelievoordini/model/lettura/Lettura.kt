package it.drlele08.prelievoordini.model.lettura

import java.util.ArrayList

class Lettura(private val idOperatore:Int,private val prodotti:ArrayList<DetailLettura>)
{
    fun getIdOperatore():Int
    {
        return idOperatore
    }

    fun getProdotti():ArrayList<DetailLettura>
    {
        return prodotti
    }
}