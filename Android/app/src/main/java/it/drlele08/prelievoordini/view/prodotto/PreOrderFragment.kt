package it.drlele08.prelievoordini.view.prodotto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doOnTextChanged
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import it.drlele08.prelievoordini.R
import it.drlele08.prelievoordini.Utilita
import it.drlele08.prelievoordini.controller.prodotto.ProdottoAdapter
import it.drlele08.prelievoordini.controller.prodotto.ProdottoController
import it.drlele08.prelievoordini.controller.prodotto.ProdottoDelegate
import it.drlele08.prelievoordini.model.Prodotto
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle


class PreOrderFragment : Fragment(),ProdottoDelegate
{
    private fun showFilter()
    {
        MaterialDialog(requireContext(), BottomSheet()).show {
            title(R.string.filtri)
            customView(R.layout.filter_modal, scrollable = true, horizontalPadding = true)
            positiveButton(R.string.applica) { dialog ->
                val spinnerCategoria: Spinner =dialog.getCustomView().findViewById(R.id.spinnerFilterCategoria)
                val spinnerOrdina: Spinner =dialog.getCustomView().findViewById(R.id.spinnerFilterOrdinamento)
                val selectedCategoria=spinnerCategoria.selectedItemId.toInt()
                val selectedOrdina=spinnerOrdina.selectedItemId.toInt()
                categoria=selectedCategoria
                filtro=selectedOrdina+1
                pagina=0
                listProdotti.clear()
                getProdotti()
            }
            negativeButton(R.string.annulla)
        }
    }

    private fun getProdotti()
    {
        var idUtente=-1
        var tokenAuth=""
        if(Utilita.user != null)
        {
            idUtente= Utilita.user!!.getIdUtente()
            tokenAuth= Utilita.user!!.getTokenAuth()
        }
        ProdottoController().getPreOrderProdotti(idUtente,tokenAuth,pagina,filtro,desc,categoria,queue,{ list->
            maxPagina = list.size <= 0
            for(item in list)
            {
                listProdotti.add(item)
            }
            adapter.updateVett(listProdotti)
        },{mess->
            MotionToast.darkToast(requireActivity(),getString(R.string.errore),mess,
                MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
        })
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View
    {
        return inflater.inflate(R.layout.fragment_pre_order,container,false)
    }
    private lateinit var viewProdotti: RecyclerView
    private lateinit var btnFilter: ImageView
    private lateinit var textNome: EditText
    private lateinit var listProdotti:ArrayList<Prodotto>
    private lateinit var queue: RequestQueue
    private lateinit var adapter: ProdottoAdapter
    private lateinit var viewGlo:View
    private var pagina=0
    private var filtro=6
    private var desc=""
    private var categoria=0
    private var maxPagina=false
    private lateinit var layoutManager: LinearLayoutManager
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        viewProdotti=view.findViewById(R.id.listProdottiPreOrder)
        btnFilter=view.findViewById(R.id.btnFiltroProdPreOrder)
        viewGlo=view
        textNome=view.findViewById(R.id.inputDescProdPreOrder)
        queue= Volley.newRequestQueue(requireContext())
        pagina=0
        layoutManager= LinearLayoutManager(requireContext())
        viewProdotti.layoutManager = layoutManager
        listProdotti=ArrayList()
        adapter= ProdottoAdapter(listProdotti,this,requireContext())
        viewProdotti.adapter=adapter
        btnFilter.setOnClickListener{
            showFilter()
        }
        getProdotti()
        textNome.doOnTextChanged { text, _, _, _ ->
            if(text.toString()!=desc)
            {
                pagina=0
                listProdotti.clear()
                desc = if(text.toString().length>2)
                    text.toString()
                else
                    ""
                getProdotti()
            }
        }

        viewProdotti.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
                val maxVisible=layoutManager.findLastVisibleItemPosition()
                val total = adapter.itemCount
                if(total>5)
                {
                    if ((pastVisibleItem>(total-8) || (total-maxVisible)<3) && !maxPagina)
                    {
                        pagina++
                        getProdotti()
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }
    override fun onProductClick(prodotto: Prodotto)
    {
        if(Utilita.user != null)
        {
            listProdotti.clear()
            val b=Bundle()
            b.putInt("idArticolo",prodotto.getIdArticolo())
            Navigation.findNavController(viewGlo).navigate(R.id.detailProdottoFragment,b)
        }
    }
}