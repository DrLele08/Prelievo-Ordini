package it.drlele08.prelievoordini.view.prodotto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.mcdev.quantitizerlibrary.HorizontalQuantitizer
import com.mcdev.quantitizerlibrary.QuantitizerListener
import it.drlele08.prelievoordini.R
import it.drlele08.prelievoordini.Utilita
import it.drlele08.prelievoordini.controller.carrello.CarrelloController
import it.drlele08.prelievoordini.controller.prodotto.ProdottoController
import it.drlele08.prelievoordini.model.Prodotto
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import kotlin.math.roundToInt

class DetailProdottoFragment : Fragment()
{
    private fun updateQnt(value:Int)
    {
        var prezzo=prodotto.getPrezzoIvato()
        if(btnTipoQnt.isChecked)
        {
            qntScelta=value*prodotto.getQntConfezione()
            prezzo=prodotto.getPrezzoIvato()*prodotto.getQntConfezione()
            val qntDisp=prodotto.getQntDisp()/prodotto.getQntConfezione()
            textQntDisponibile.text="$qntDisp pz"
            stepperQnt.maxValue=qntDisp
        }
        else
        {
            qntScelta=value
            stepperQnt.maxValue=prodotto.getQntDisp()
            textQntDisponibile.text="${prodotto.getQntDisp()} pz"
        }
        val quadagno=(prodotto.getPrezzoConsigliato()-prodotto.getPrezzoIvato())*qntScelta
        val percQuad=(((prodotto.getPrezzoConsigliato()-prodotto.getPrezzoIvato())/prodotto.getPrezzoIvato())*100).roundToInt()
        textROI.text="$percQuad% (${String.format("%.2f",quadagno)}€)"
        textPrezzo.text="${String.format("%.2f",prezzo)}€"
    }
    private fun loadInfo()
    {
        textNome.text=prodotto.getDescrizione()
        textQntCartone.text="${prodotto.getQntConfezione()} pz"
        updateQnt(qntScelta)
        if(prodotto.getQntConfezione()>prodotto.getQntDisp())
        {
            btnTipoQnt.isClickable=false
        }
        else
        {
            btnTipoQnt.setOnCheckedChangeListener { _, _ ->
                updateQnt(1)
            }
        }
        Glide.with(requireContext()).load("${Utilita.host}/img/articoli/${prodotto.getIdArticolo()}.jpg").circleCrop().into(imageProd)
        imageInfo.setOnClickListener{
            loadSpecifiche()
        }
        if(Utilita.user!!.getTipoUtente() == 3)
        {
            btnAddCart.setOnClickListener{
                addToCart()
            }
            stepperQnt.visibility=View.VISIBLE
            btnAddCart.visibility=View.VISIBLE
            stepperQnt.setQuantitizerListener(object: QuantitizerListener {
                override fun onIncrease() {}

                override fun onDecrease() {}

                override fun onValueChanged(value: Int)
                {
                    updateQnt(value)
                }
            })
        }
    }

    private fun loadSpecifiche()
    {
        MaterialDialog(requireContext()).show {
            var mess=""
            mess+="<b>"+getString(R.string.prezzo_cons)+":</b> ${prodotto.getPrezzoConsigliato()}€<br>"
            mess+="<b>"+getString(R.string.lunghezza)+":</b> ${prodotto.getLunghezza()}cm<br>"
            mess+="<b>"+getString(R.string.altezza)+":</b> ${prodotto.getAltezza()}cm<br>"
            mess+="<b>"+getString(R.string.larghezza)+":</b> ${prodotto.getProfondita()}cm<br>"
            mess+="<b>"+getString(R.string.volume)+":</b> ${prodotto.getVolume()}<br>"
            mess+="<b>"+getString(R.string.peso)+":</b> ${prodotto.getPesoGrammi()} "+getString(R.string.grammi)
            title(R.string.specifiche_prodotto)
            message(text = mess){
                html()
            }
            positiveButton(text = "Ok")
        }
    }
    private fun addToCart()
    {
        val idUtente=Utilita.user!!.getIdUtente()
        val tokenAuth=Utilita.user!!.getTokenAuth()
        btnAddCart.isEnabled=false
        CarrelloController().addToCart(idUtente,tokenAuth,prodotto.getIdArticolo(),qntScelta,queue,{
            btnAddCart.isEnabled=true
            MotionToast.darkToast(requireActivity(),getString(R.string.fatto),getString(R.string.aggiunto_al_carrello),
                MotionToastStyle.SUCCESS,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
        },{mess ->
            btnAddCart.isEnabled=true
            MotionToast.darkToast(requireActivity(),getString(R.string.errore),mess,
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
    private lateinit var btnTipoQnt:ToggleButton
    private lateinit var textPrezzo:TextView
    private lateinit var imageInfo:ImageView
    private lateinit var textROI:TextView
    private var qntScelta=1
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        qntScelta=1
        imageProd=view.findViewById(R.id.imageDetailProd)
        textNome=view.findViewById(R.id.textDetailNome)
        btnAddCart=view.findViewById(R.id.btnDetailAddCart)
        stepperQnt=view.findViewById(R.id.stepperQnt)
        imageInfo=view.findViewById(R.id.imageInfoDetailProd)
        textQntDisponibile=view.findViewById(R.id.textDetailQntDisp)
        btnTipoQnt=view.findViewById(R.id.toggleButton)
        textQntCartone=view.findViewById(R.id.textDetailQntCartone)
        textPrezzo=view.findViewById(R.id.textDetailPrezzo)
        textROI=view.findViewById(R.id.textViewDetailROI)
        stepperQnt.setValueBackgroundColor(R.color.white)
        stepperQnt.minValue=1
        val idArticolo=requireArguments().getInt("idArticolo",-1)
        queue=Volley.newRequestQueue(requireContext())
        ProdottoController().getProdottoById(Utilita.user!!.getIdUtente(),Utilita.user!!.getTokenAuth(),idArticolo,queue,{prod->
            prodotto=prod
            loadInfo()
        },{mess->
            MotionToast.darkToast(requireActivity(),getString(R.string.errore),mess,
                MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
        })
    }
}