package it.drlele08.prelievoordini.model.lettura

import java.io.Serializable

class LetturaProdotto(private val idRigaOrdine:Int=-1,private val ksArticolo:Int=-1,private val tipoEvento:Int,private val qnt:Int=-1,private val note:String=""):Serializable
{
    companion object
    {
        const val LETTURA_CONFERMATA=1
        const val LETTURA_ERRATA=2
        const val CHIUSURA_MOTIVATA=3
        const val DISPOSTIVO_SCOLLEGATO=4
        const val DISPOSITIVO_COLLEGATO=5
        const val LETTURA_TERMINATA=6
    }
    fun getIdRigaOrdine():Int
    {
        return idRigaOrdine
    }

    fun getIdArticolo():Int
    {
        return ksArticolo
    }

    fun getTipoEvento():Int
    {
        return tipoEvento
    }

    fun getQnt():Int
    {
        return qnt
    }

    fun getNote():String
    {
        return note
    }
}