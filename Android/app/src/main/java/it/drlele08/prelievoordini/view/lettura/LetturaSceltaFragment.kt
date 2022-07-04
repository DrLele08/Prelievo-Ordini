package it.drlele08.prelievoordini.view.lettura

import android.app.Dialog
import android.content.Context.MODE_PRIVATE
import android.content.DialogInterface
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mcdev.quantitizerlibrary.HorizontalQuantitizer
import it.drlele08.prelievoordini.R
import it.drlele08.prelievoordini.Utilita
import it.drlele08.prelievoordini.controller.lettura.LetturaController
import it.drlele08.prelievoordini.controller.prodotto.ProdottoController
import it.drlele08.prelievoordini.model.lettura.Lettura
import it.drlele08.prelievoordini.model.lettura.LetturaProdotto
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.lang.reflect.Type


class LetturaSceltaFragment : Fragment()
{
    private fun uscitaMotivata()
    {
        val dialog=Dialog(requireContext())
        dialog.setContentView(R.layout.item_uscita_motivata)
        dialog.setCancelable(false)
        val btnAnnulla=dialog.findViewById<Button>(R.id.btnUscitaAnnulla)
        val btnConferma=dialog.findViewById<Button>(R.id.btnUscitaConferma)
        val inputMotivo=dialog.findViewById<EditText>(R.id.inputChiusuraMotivo)
        btnAnnulla.setOnClickListener{
            dialog.hide()
        }
        btnConferma.setOnClickListener{
            val motivo=inputMotivo.text.toString().trim()
            if(motivo.isNotEmpty())
            {
                dialog.hide()
                detailArretrati.add(LetturaProdotto(tipoEvento = LetturaProdotto.CHIUSURA_MOTIVATA, note = motivo))
                sendLetture(true)
            }
        }
        dialog.show()
    }
    private fun addQntContinuo()
    {
        val actualProd=lettura.getProdotti()[currentProd]
        detailArretrati.add(LetturaProdotto(actualProd.getIdRigaOrdine(),actualProd.getIdArticolo(),LetturaProdotto.LETTURA_CONFERMATA,currentQnt))
        currentQnt=0
        currentProd++
        if(lettura.getProdotti().size==currentProd)
        {
            detailArretrati.add(LetturaProdotto(tipoEvento = LetturaProdotto.LETTURA_TERMINATA))
            sendLetture(true)
        }
        else
        {
            sendLetture(false)
            updateProd()
        }
    }
    private fun aggiungiQnt()
    {
        val actualProd=lettura.getProdotti()[currentProd]
        if(actualProd.getQntOrdinata()>currentQnt && currentQnt<qntDisponibile)
        {
            MaterialDialog(requireContext()).show {
                title(R.string.attenzione)
                message(R.string.avviso_qnt_lettura_not_find)
                positiveButton(R.string.si){
                    addQntContinuo()
                }
                negativeButton(text = "No"){
                    addQntContinuo()
                }
            }
        }
        else
        {
            addQntContinuo()
        }
    }
    private fun updateProd()
    {
        qntDisponibile=0
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
            posScaf+="${actualPos.getScaffale()} [${actualPos.getQnt()} Pz]\n"
            qntDisponibile+=actualPos.getQnt()
            qntRimanente-=actualPos.getQnt()
            i++
        }
        textScaffale.text=getString(R.string.scaffale,posScaf)
        textReparto.text=getString(R.string.reparto,listPosizioni[0].getIdReparto())
        if(lettura.getProdotti().size==(currentProd+1))
        {
            btnEsci.visibility=View.GONE
            btnContinua.text=getString(R.string.termina)
        }
        else
        {
            btnEsci.visibility=View.VISIBLE
            btnContinua.text=getText(R.string.continua)
        }
    }

    private fun saveDatiOffline()
    {
        val json: String = Gson().toJson(detailArretrati)
        Utilita.saveDato(requireContext(),keyLettureRecover,json)
    }

    private fun resetDatiOffline()
    {
        Utilita.removeDato(requireContext(),keyLettureRecover)
    }

    private fun sendLetture(showAlert:Boolean)
    {
        LetturaController().updateLettura(Utilita.user!!.getIdUtente(),Utilita.user!!.getTokenAuth(),lettura.getIdOperatore(),detailArretrati,queue,{
            resetDatiOffline()
            if(showAlert)
            {
                MotionToast.darkToast(requireActivity(),getString(R.string.fatto),getString(R.string.operazione_completata),
                    MotionToastStyle.SUCCESS,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
                requireActivity().onBackPressed()
            }
        },{mess ->
            saveDatiOffline()
            if(showAlert)
            {
                MotionToast.darkToast(requireActivity(),getString(R.string.errore),mess,
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
            }
        })
    }
    private fun showViewQnt()
    {
        val dialog=Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_qnt_lettura)
        val btnConferma=dialog.findViewById(R.id.btnConfermaPickerLettura) as TextView
        val pickerQnt=dialog.findViewById(R.id.stepperQnt) as HorizontalQuantitizer
        val btnEsci=dialog.findViewById(R.id.btnEsciPickerLettura) as TextView
        val btnReset=dialog.findViewById(R.id.btnResetPickerLettura) as TextView
        val inputEAN=dialog.findViewById<EditText>(R.id.editTextTextPersonName)
        dialog.setOnKeyListener(DialogInterface.OnKeyListener { _, _, keyEvent ->
            Log.d("EAN",keyEvent.toString())
            if(keyEvent.action==KeyEvent.ACTION_DOWN)
            {
                inputEAN.requestFocus()
            }
            else if(keyEvent.action==KeyEvent.ACTION_UP)
            {
                val text=inputEAN.text.toString().trim()
                inputEAN.setText("")
                if(text.isNotEmpty())
                {
                    ProdottoController().getProdottoByEan(Utilita.user!!.getIdUtente(),Utilita.user!!.getTokenAuth(),text,queue,{prodotto, qntEan ->
                        val actualProd=lettura.getProdotti()[currentProd]
                        if(actualProd.getIdArticolo()==prodotto.getIdArticolo())
                        {
                            detailArretrati.add(LetturaProdotto(actualProd.getIdRigaOrdine(),actualProd.getIdArticolo(),LetturaProdotto.LETTURA_CONFERMATA,qntEan))
                            //ToDo Continua
                        }
                        else
                        {
                            detailArretrati.add(LetturaProdotto(tipoEvento = LetturaProdotto.LETTURA_ERRATA))
                            //ToDo Show Errore
                        }
                    },{mess ->
                        MotionToast.darkToast(requireActivity(),getString(R.string.errore),mess,
                            MotionToastStyle.SUCCESS,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
                    })
                    Toast.makeText(requireContext(),"EAN: $text",Toast.LENGTH_LONG).show()
                    dialog.hide()
                }
            }
            return@OnKeyListener false
        })
        btnEsci.setOnClickListener{
            dialog.dismiss()
        }
        btnConferma.setOnClickListener{
            val qnt=pickerQnt.value
            dialog.hide()
            if(qnt>0)
            {
                currentQnt+=qnt
                updateProd()
            }
        }
        btnReset.setOnClickListener{
            currentQnt=0
            dialog.hide()
            updateProd()
        }
        dialog.show()
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
    private var qntDisponibile=0
    private val keyLettureRecover="LETTURA_RECOVERY"
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
        val sharedPreferences: SharedPreferences =
            requireContext().getSharedPreferences(Utilita.saveKey,MODE_PRIVATE)
        val jsonRecover=sharedPreferences.getString(keyLettureRecover,null)
        detailArretrati=if(jsonRecover==null)
        {
            ArrayList()
        }
        else
        {
            val type: Type = object : TypeToken<ArrayList<LetturaProdotto?>?>() {}.type
            Gson().fromJson(jsonRecover, type)
        }
        detailArretrati.add(LetturaProdotto(tipoEvento = LetturaProdotto.DISPOSITIVO_COLLEGATO))
        updateProd()
        textTimer.base=SystemClock.elapsedRealtime()
        textTimer.start()
        btnAdd.setOnClickListener{
            showViewQnt()
        }
        btnContinua.setOnClickListener{
            val actualProd=lettura.getProdotti()[currentProd]
            if(currentQnt==actualProd.getQntOrdinata())
            {
                aggiungiQnt()
            }
            else
            {
                MaterialDialog(requireContext()).show {
                    title(R.string.conferma_lettura)
                    message(text = getString(R.string.conferma_lettura_detail,currentQnt,actualProd.getQntOrdinata()))
                    positiveButton(R.string.si){
                        aggiungiQnt()
                    }
                    negativeButton(R.string.annulla)
                }
            }
        }
        btnEsci.setOnClickListener{
            uscitaMotivata()
        }
    }

}