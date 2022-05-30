package it.drlele08.prelievoordini.controller.prodotto

import it.drlele08.prelievoordini.model.Prodotto

interface ProdottoDelegate
{
    fun onProductClick(prodotto: Prodotto)
}