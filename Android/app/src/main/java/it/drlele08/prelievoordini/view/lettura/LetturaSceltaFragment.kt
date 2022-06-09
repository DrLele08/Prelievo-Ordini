package it.drlele08.prelievoordini.view.lettura

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import it.drlele08.prelievoordini.R
import it.drlele08.prelievoordini.Utilita
import it.drlele08.prelievoordini.controller.lettura.LetturaController
import it.drlele08.prelievoordini.model.lettura.DetailLettura
import it.drlele08.prelievoordini.model.lettura.Lettura
import it.drlele08.prelievoordini.model.lettura.LetturaProdotto
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle


class LetturaSceltaFragment : Fragment()
{
    @SuppressLint("CheckResult")
    private fun uscitaMotivata()
    {
        val dialog=MaterialDialog(requireContext()).show {
            title(text = "Conferma chiusura")
            input{ _, text ->
                val motivo=text.toString()
                detailArretrati.add(LetturaProdotto(tipoEvento = LetturaProdotto.DISPOSTIVO_SCOLLEGATO, note = motivo))
                sendLetture(true)
            }
            positiveButton(R.string.continua)
            negativeButton(text = "Annulla")
        }
        val text=dialog.getInputField()
        text.hint="Inserisci il motivo della chiusura"
        text.setBackgroundColor(Color.WHITE)
        text.setTextColor(Color.BLACK)
    }

    private fun continua()
    {
        if(currentProd<(lettura.getProdotti().size-1))
        {
            currentProd++
            updateProd()
        }
        else
        {
            //Finito
        }
    }

    private fun aggiungiQnt()
    {

    }
    private fun updateProd()
    {
        textCounter.text="${currentProd+1}/${lettura.getProdotti().size}"
        val actualProd=lettura.getProdotti()[currentProd]
        textNomeProd.text=actualProd.getDescrizione()
        Glide.with(requireContext()).load("${Utilita.host}/img/articoli/${actualProd.getIdArticolo()}.jpg").circleCrop().into(imageFoto)
        val qntOrdinata=actualProd.getQntOrdinata()
        when (currentQnt)
        {
            0 -> textQntProd.setTextColor(Color.RED)
            qntOrdinata -> textQntProd.setTextColor(Color.GREEN)
            else -> textQntProd.setTextColor(Color.YELLOW)
        }
        textQntProd.text="$currentQnt/$qntOrdinata Pz"
        val listPosizioni=actualProd.getVettPosizioni()
        var posScaf=""
        var qntRimanente=qntOrdinata
        var i=0
        while(qntRimanente>=0 && i<listPosizioni.size)
        {
            val actualPos=listPosizioni[i]
            posScaf+="${actualPos.getScaffale()} [${actualPos.getQnt()} Pz]"
            if(i>0)
                posScaf+=" -- "
            qntRimanente-=actualPos.getQnt()
            i++
        }
        textScaffale.text="Scaffale: $posScaf"
        textReparto.text="Reparto: ${listPosizioni[0].getIdReparto()}"

    }

    private fun sendLetture(showAlert:Boolean)
    {
        LetturaController().updateLettura(Utilita.user!!.getIdUtente(),Utilita.user!!.getTokenAuth(),lettura.getIdOperatore(),detailArretrati,queue,{
            if(showAlert)
            {
                MotionToast.darkToast(requireActivity(),"Fatto","Operazione effettuata",
                    MotionToastStyle.SUCCESS,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
            }
        },{mess ->
            if(showAlert)
            {
                MotionToast.darkToast(requireActivity(),"Errore",mess,
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
            }
        })
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_lettura_scelta, container, false)
    }
    private lateinit var lettura:Lettura
    private lateinit var imageFoto:ImageView
    private lateinit var textTimer:Chronometer
    private lateinit var textCounter:TextView
    private lateinit var textNomeProd:TextView
    private lateinit var textQntProd:TextView
    private lateinit var btnEsci:Button
    private lateinit var btnContinua:Button
    private lateinit var btnAdd:Button
    private lateinit var textReparto:TextView
    private lateinit var textScaffale:TextView
    private lateinit var detailArretrati:ArrayList<LetturaProdotto>
    private lateinit var queue: RequestQueue
    private var currentProd=0
    private var currentQnt=0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        currentProd=0
        currentQnt=0
        lettura=requireArguments().getSerializable("lettura") as Lettura
        queue=Volley.newRequestQueue(requireContext())
        imageFoto=view.findViewById(R.id.imageDetailLetturaProd)
        textCounter=view.findViewById(R.id.textDetailLetturaCounter)
        textTimer=view.findViewById(R.id.textDetailLetturaTimer)
        textNomeProd=view.findViewById(R.id.textDetailLetturaNomeProd)
        textQntProd=view.findViewById(R.id.textDetailLetturaQnt)
        btnEsci=view.findViewById(R.id.btnDetailLetturaEsci)
        btnContinua=view.findViewById(R.id.btnDetailLetturaContinua)
        btnAdd=view.findViewById(R.id.btnDetailLetturaAdd)
        textReparto=view.findViewById(R.id.textDetailLetturaReparto)
        textScaffale=view.findViewById(R.id.textDetailLetturaScaffale)
        detailArretrati=ArrayList()
        detailArretrati.add(LetturaProdotto(tipoEvento = LetturaProdotto.DISPOSITIVO_COLLEGATO))
        updateProd()
        textTimer.base=SystemClock.elapsedRealtime()
        textTimer.start()
        btnAdd.setOnClickListener{
            aggiungiQnt()
        }
        btnContinua.setOnClickListener{
            continua()
        }
        btnEsci.setOnClickListener{
            uscitaMotivata()
        }
    }

    override fun onDetach()
    {
        if(detailArretrati[detailArretrati.size-1].getTipoEvento()!=LetturaProdotto.DISPOSTIVO_SCOLLEGATO)
        {
            detailArretrati.add(LetturaProdotto(tipoEvento = LetturaProdotto.DISPOSTIVO_SCOLLEGATO, note = "Chiusa sessione"))
            sendLetture(false)
        }
        super.onDetach()
    }
}