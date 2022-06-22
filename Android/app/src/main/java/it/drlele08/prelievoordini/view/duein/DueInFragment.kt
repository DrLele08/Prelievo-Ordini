package it.drlele08.prelievoordini.view.duein

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import it.drlele08.prelievoordini.R
import it.drlele08.prelievoordini.controller.duein.DueInAdapter
import it.drlele08.prelievoordini.controller.duein.DueInController
import it.drlele08.prelievoordini.controller.duein.DueInDelegate
import it.drlele08.prelievoordini.model.duein.DueIn
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.util.ArrayList

class DueInFragment : Fragment(),DueInDelegate
{
    private fun getDueIn()
    {
        DueInController().getDueIn(queue,{list->
            vettDue=list
            adapter= DueInAdapter(vettDue,this)
            viewDue.layoutManager = LinearLayoutManager(requireContext())
            viewDue.adapter=adapter
        },{mess ->
            MotionToast.darkToast(requireActivity(),getString(R.string.errore),mess,
                MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
        })
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View
    {
        return inflater.inflate(R.layout.fragment_duein,container,false)
    }
    private lateinit var queue:RequestQueue
    private lateinit var vettDue:ArrayList<DueIn>
    private lateinit var adapter: DueInAdapter
    private lateinit var viewDue:RecyclerView
    private lateinit var viewFrag: View
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        queue=Volley.newRequestQueue(requireContext())
        viewDue=view.findViewById(R.id.listDueGen)
        viewFrag=view
        getDueIn()
    }

    override fun onItemClick(item: DueIn)
    {
        val b=Bundle()
        b.putInt("idDue",item.getIdDue())
        Navigation.findNavController(viewFrag).navigate(R.id.dueInDetailFragment,b)
    }
}