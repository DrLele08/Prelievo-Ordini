package it.drlele08.prelievoordini.model

class Categoria(private val idCategoria:Int,private val nome:String)
{
    fun getIdCategoria():Int
    {
        return idCategoria
    }

    fun getNome():String
    {
        return nome
    }
}