package it.drlele08.prelievoordini.model.lettura

import it.drlele08.prelievoordini.model.PosizioneProdotto
import java.util.ArrayList

class DetailLettura(private val idRigaOrdine:Int,private val idArticolo:Int,private val descrizione:String,private val qntOrdinata:Int,private val vettPosizioni:ArrayList<PosizioneProdotto>)
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

    fun getQntOrdinata():Int
    {
        return qntOrdinata
    }

    fun getVettPosizioni():ArrayList<PosizioneProdotto>
    {
        return vettPosizioni
    }
}