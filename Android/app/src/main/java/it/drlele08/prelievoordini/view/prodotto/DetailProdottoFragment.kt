package it.drlele08.prelievoordini.view.prodotto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import it.drlele08.prelievoordini.R
import it.drlele08.prelievoordini.Utilita
import it.drlele08.prelievoordini.controller.prodotto.ProdottoController
import it.drlele08.prelievoordini.model.Prodotto
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class DetailProdottoFragment : Fragment()
{
    private fun loadInfo()
    {
        Toast.makeText(requireContext(),"Nome: "+prodotto.getDescrizione(),Toast.LENGTH_SHORT).show()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_detail_prodotto, container, false)
    }
    private lateinit var queue: RequestQueue
    private lateinit var prodotto:Prodotto
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        val idArticolo=requireArguments().getInt("idArticolo",-1)
        queue=Volley.newRequestQueue(requireContext())
        ProdottoController().getProdottoById(Utilita.user!!.getIdUtente(),Utilita.user!!.getTokenAuth(),idArticolo,queue,{prod->
            prodotto=prod
            loadInfo()
        },{mess->
            MotionToast.darkToast(requireActivity(),"Errore",mess,
                MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
        })
    }
}