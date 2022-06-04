package it.drlele08.prelievoordini.controller.duein

import it.drlele08.prelievoordini.model.duein.DueIn

interface DueInDelegate
{
    fun onItemClick(item:DueIn)
}