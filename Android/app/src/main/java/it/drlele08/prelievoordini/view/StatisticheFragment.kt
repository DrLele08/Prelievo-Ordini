package it.drlele08.prelievoordini.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import it.drlele08.prelievoordini.R
import it.drlele08.prelievoordini.Utilita
import it.drlele08.prelievoordini.controller.UtenteController
import it.drlele08.prelievoordini.controller.statistiche.StatisticheAdapter
import it.drlele08.prelievoordini.controller.statistiche.StatisticheController
import it.drlele08.prelievoordini.controller.statistiche.StatisticheDelegate
import it.drlele08.prelievoordini.model.UtenteMini
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle


class StatisticheFragment : Fragment(),StatisticheDelegate
{
    private fun loadUser()
    {
        UtenteController().getAllOperatori(Utilita.user!!.getIdUtente(),Utilita.user!!.getTokenAuth(),queue,{vettUtente ->
            val adapter=StatisticheAdapter(vettUtente,this)
            recyclerUtente.layoutManager = LinearLayoutManager(requireContext())
            recyclerUtente.adapter=adapter
        },{mess ->
            MotionToast.darkToast(requireActivity(),getString(R.string.errore),mess,
                MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
        })
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_statistiche, container, false)
    }
    private lateinit var recyclerUtente:RecyclerView
    private lateinit var queue: RequestQueue
    private lateinit var textMostLetture:TextView
    private lateinit var textMostError:TextView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        recyclerUtente=view.findViewById(R.id.recyclerUserStat)
        textMostLetture=view.findViewById(R.id.textStatMostLetture)
        textMostError=view.findViewById(R.id.textStatMostError)
        queue=Volley.newRequestQueue(requireContext())
        loadUser()
        StatisticheController().getGenericStat(Utilita.user!!.getIdUtente(),Utilita.user!!.getTokenAuth(),queue,
            { messLett, mostErr ->
                val nomeMostLett=messLett.getJSONObject(0).getString("Nome")
                val numMostLett=messLett.getJSONObject(0).getInt("Letture")
                textMostLetture.text=getString(R.string.dippiulett,nomeMostLett,numMostLett)
                val nomeMostErr=mostErr.getString("Nome")
                val numMostErr=mostErr.getInt("Letture")
                textMostError.text=getString(R.string.dippiuerr,nomeMostErr,numMostErr)
            },{ mess ->
                MotionToast.darkToast(requireActivity(),getString(R.string.errore),mess,
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
            })
    }

    override fun onItemClick(utenteMini: UtenteMini)
    {
        StatisticheController().getStatUtente(Utilita.user!!.getIdUtente(),Utilita.user!!.getTokenAuth(),utenteMini.getIdUtente(),queue,
            {generiche, dettagli ->
                var messaggio=getString(R.string.generiche)
                val objOrdiniEffettuati=generiche.getJSONObject(0)
                val objLettureTotali=generiche.getJSONObject(1)
                messaggio+=getString(R.string.ordini_gest,objOrdiniEffettuati.getInt("Valore"))
                messaggio+=getString(R.string.operazioni_gest,objLettureTotali.getInt("Valore"))
                messaggio+=getString(R.string.dettagli)
                for(i in 1 until dettagli.length())
                {
                    val obj=dettagli.getJSONObject(i)
                    messaggio+="${obj.getString("Evento")}: ${obj.getInt("Valore")}\n"
                }
                MaterialDialog(requireContext()).show {
                    title(text = getString(R.string.nomestat,utenteMini.getNome()))
                    message(text = messaggio)
                    positiveButton(text = "Ok")
                }
            },{mess ->
                MotionToast.darkToast(requireActivity(),getString(R.string.errore),mess,
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
            })
    }
}