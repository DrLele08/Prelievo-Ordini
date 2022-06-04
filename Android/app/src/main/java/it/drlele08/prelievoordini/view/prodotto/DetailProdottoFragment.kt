package it.drlele08.prelievoordini.view.prodotto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.mcdev.quantitizerlibrary.HorizontalQuantitizer
import it.drlele08.prelievoordini.R
import it.drlele08.prelievoordini.Utilita
import it.drlele08.prelievoordini.controller.carrello.CarrelloController
import it.drlele08.prelievoordini.controller.prodotto.ProdottoController
import it.drlele08.prelievoordini.model.Prodotto
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class DetailProdottoFragment : Fragment()
{
    private fun loadInfo()
    {
        textNome.text=prodotto.getDescrizione()
        Glide.with(requireContext()).load("${Utilita.host}/img/articoli/${prodotto.getIdArticolo()}.jpg").circleCrop().into(imageProd)
        btnAddCart.setOnClickListener{
            addToCart()
        }
    }

    private fun loadSpecifiche()
    {

    }
    private fun addToCart()
    {
        val qnt=1 //ToDo Cambiare
        val idUtente=Utilita.user!!.getIdUtente()
        val tokenAuth=Utilita.user!!.getTokenAuth()
        btnAddCart.isEnabled=false
        CarrelloController().addToCart(idUtente,tokenAuth,prodotto.getIdArticolo(),qnt,queue,{
            btnAddCart.isEnabled=true
            MotionToast.darkToast(requireActivity(),"Fatto","Prodotto aggiunto al carrello",
                MotionToastStyle.SUCCESS,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
        },{mess ->
            btnAddCart.isEnabled=true
            MotionToast.darkToast(requireActivity(),"Errore",mess,
                MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
        })
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_detail_prodotto, container, false)
    }
    private lateinit var queue: RequestQueue
    private lateinit var prodotto:Prodotto
    private lateinit var imageProd:ImageView
    private lateinit var textNome:TextView
    private lateinit var btnAddCart:Button
    private lateinit var stepperQnt:HorizontalQuantitizer
    private lateinit var textQntCartone:TextView
    private lateinit var textQntDisponibile:TextView
    private lateinit var textInfoQnt:TextView
    private lateinit var textSpecifiche:TextView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        imageProd=view.findViewById(R.id.imageDetailProd)
        textNome=view.findViewById(R.id.textDetailNome)
        btnAddCart=view.findViewById(R.id.btnDetailAddCart)
        stepperQnt=view.findViewById(R.id.stepperQnt)
        textInfoQnt=view.findViewById(R.id.textDetailInfoQnt)
        textQntDisponibile=view.findViewById(R.id.textDetailQntDisp)
        textQntCartone=view.findViewById(R.id.textDetailQntCartone)
        textSpecifiche=view.findViewById(R.id.textDetailSpecifiche)
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