package it.drlele08.prelievoordini.view.lettura

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import it.drlele08.prelievoordini.R
import it.drlele08.prelievoordini.model.lettura.Lettura


class LetturaSceltaFragment : Fragment()
{


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_lettura_scelta, container, false)
    }
    private lateinit var lettura:Lettura
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        lettura=requireArguments().getSerializable("lettura") as Lettura
        Toast.makeText(requireContext(),"ID Operatore: ${lettura.getIdOperatore()}",Toast.LENGTH_SHORT).show()
    }
}