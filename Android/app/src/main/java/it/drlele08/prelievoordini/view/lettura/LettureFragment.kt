package it.drlele08.prelievoordini.view.lettura

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import it.drlele08.prelievoordini.R
import it.drlele08.prelievoordini.Utilita
import it.drlele08.prelievoordini.controller.lettura.LetturaAdapter
import it.drlele08.prelievoordini.controller.lettura.LetturaController
import it.drlele08.prelievoordini.controller.lettura.LetturaDelegate
import it.drlele08.prelievoordini.model.lettura.InfoLettura
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.util.ArrayList

class LettureFragment : Fragment(), LetturaDelegate
{
    private fun scegliLettura(idOrdine:Int)
    {
        MaterialDialog(requireContext()).show {
            title(text = "Conferma ordine")
            message(text = "Vuoi confermare la presa in carico per l'ordine #$idOrdine")
            positiveButton(R.string.si) { _ ->
                LetturaController().scegliLettura(Utilita.user!!.getIdUtente(),Utilita.user!!.getTokenAuth(),idOrdine,queue,{lett ->
                    val b=Bundle()
                    b.putSerializable("lettura",lett)
                    Navigation.findNavController(viewFrag).navigate(R.id.letturaSceltaFragment,b)
                },{mess ->
                    MotionToast.darkToast(requireActivity(),"Errore",mess,
                        MotionToastStyle.ERROR,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
                })
            }
            negativeButton(text = "Annulla")
        }
    }

    private fun checkLettura()
    {
        LetturaController().getLetturaInCorso(Utilita.user!!.getIdUtente(),Utilita.user!!.getTokenAuth(),queue,{ lett->
            if(lett.getIdOperatore() != -1)
            {
                MaterialDialog(requireContext()).show {
                    title(text = "Lettura in corso")
                    message(text = "Hai una lettura di un ordine in sospeso\nVuoi continuare?")
                    positiveButton(R.string.si) {
                        val b=Bundle()
                        b.putSerializable("lettura",lett)
                        Navigation.findNavController(viewFrag).navigate(R.id.letturaSceltaFragment,b)
                    }
                    negativeButton(text = "No")
                }
            }
            loadLetture()
        },{mess ->
            MotionToast.darkToast(requireActivity(),"Errore",mess,
                MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
        })
    }

    private fun loadLetture()
    {
        LetturaController().getLettureInevase(Utilita.user!!.getIdUtente(),Utilita.user!!.getTokenAuth(),queue,{ list->
            listLetture=list
            adapter=LetturaAdapter(listLetture,this)
            recyclerLetture.layoutManager=LinearLayoutManager(requireContext())
            recyclerLetture.adapter=adapter
            if(list.size>0)
            {
                animazione.visibility=View.GONE
                animazione.pauseAnimation()
                textAnimazione.visibility=View.GONE
            }
            else
            {
                animazione.visibility=View.VISIBLE
                textAnimazione.visibility=View.VISIBLE
            }
        },{mess ->
            MotionToast.darkToast(requireActivity(),"Errore",mess,
                MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
        })
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_letture, container, false)
    }
    private lateinit var queue: RequestQueue
    private lateinit var recyclerLetture:RecyclerView
    private lateinit var listLetture:ArrayList<InfoLettura>
    private lateinit var adapter: LetturaAdapter
    private lateinit var viewFrag:View
    private lateinit var animazione:LottieAnimationView
    private lateinit var textAnimazione:TextView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        animazione=view.findViewById(R.id.animationLettura)
        textAnimazione=view.findViewById(R.id.textNoLetture)
        viewFrag=view
        recyclerLetture=view.findViewById(R.id.recyclerLetture)
        queue=Volley.newRequestQueue(requireContext())
        checkLettura()
    }

    override fun onItemClick(item: InfoLettura)
    {
        scegliLettura(item.getIdOrdine())
    }
}