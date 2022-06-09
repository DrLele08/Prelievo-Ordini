package it.drlele08.prelievoordini.model.lettura

import java.io.Serializable
import java.util.ArrayList

class Lettura(private val idOperatore:Int,private var prodotti:ArrayList<DetailLettura>):Serializable
{
    init
    {
        prodotti=prodotti.filter { x->
            x.getVettPosizioni().size>0
        } as ArrayList<DetailLettura>
    }
    fun getIdOperatore():Int
    {
        return idOperatore
    }

    fun getProdotti():ArrayList<DetailLettura>
    {
        return prodotti
    }
}