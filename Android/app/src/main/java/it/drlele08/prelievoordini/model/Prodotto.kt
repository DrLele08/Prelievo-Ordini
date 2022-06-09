package it.drlele08.prelievoordini.model

import android.os.Parcelable
import java.io.Serializable

class Prodotto(private val idArticolo:Int,private val descrizione:String,private val qntDisp:Int,private val prezzoIvato:Float,private val prezzoConsigliato:Float,private val lunghezza:Int,private val altezza:Int,private val profondita:Int,private val volume: Int,private val pesoGrammi:Int,private val qntConfezione:Int)
{
    fun getIdArticolo():Int
    {
        return idArticolo
    }

    fun getDescrizione():String
    {
        return descrizione
    }

    fun getQntDisp():Int
    {
        return qntDisp
    }

    fun getPrezzoIvato():Float
    {
        return prezzoIvato
    }

    fun getPrezzoConsigliato():Float
    {
        return prezzoConsigliato
    }

    fun getLunghezza():Int
    {
        return lunghezza
    }

    fun getAltezza():Int
    {
        return altezza
    }

    fun getProfondita():Int
    {
        return profondita
    }

    fun getVolume():Int
    {
        return volume
    }

    fun getPesoGrammi():Int
    {
        return pesoGrammi
    }

    fun getQntConfezione():Int
    {
        return qntConfezione
    }
}