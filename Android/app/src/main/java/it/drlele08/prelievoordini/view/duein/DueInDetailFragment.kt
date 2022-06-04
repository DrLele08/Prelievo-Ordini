package it.drlele08.prelievoordini.view.duein

import android.os.Bundle
import android.os.TokenWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import it.drlele08.prelievoordini.R
import it.drlele08.prelievoordini.controller.duein.DueInController
import it.drlele08.prelievoordini.controller.duein.DueInDetailAdapter
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle


class DueInDetailFragment : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_due_in_detail, container, false)
    }
    private lateinit var viewDetailDue:RecyclerView
    private lateinit var queue: RequestQueue
    private lateinit var adapter: DueInDetailAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        viewDetailDue=view.findViewById(R.id.listDetailDue)
        queue=Volley.newRequestQueue(requireContext())
        val idDueIn=requireArguments().getInt("idDue",-1)
        if(idDueIn != -1)
        {
            DueInController().getDetailDueIn(idDueIn, queue,{due->
                adapter=DueInDetailAdapter(due,requireContext())
                viewDetailDue.layoutManager = LinearLayoutManager(requireContext())
                viewDetailDue.adapter=adapter
            },{mess ->
                MotionToast.darkToast(requireActivity(),"Errore",mess,
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
            })
        }
    }
}