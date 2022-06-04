package it.drlele08.prelievoordini.controller.lettura

import it.drlele08.prelievoordini.model.lettura.InfoLettura

interface LetturaDelegate
{
    fun onItemClick(item:InfoLettura)
}