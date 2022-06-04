package it.drlele08.prelievoordini.model.lettura

import java.io.Serializable

class LetturaProdotto(private val idRigaOrdine:Int=-1,private val ksArticolo:Int=-1,private val tipoEvento:Int,private val qnt:Int=-1,private val note:String=""):Serializable
{
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