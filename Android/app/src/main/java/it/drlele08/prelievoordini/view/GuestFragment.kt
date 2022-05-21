package it.drlele08.prelievoordini.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import it.drlele08.prelievoordini.R

class GuestFragment : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View
    {
        return inflater.inflate(R.layout.fragment_guest,container,false)
    }
    private lateinit var btnLogin:Button
    private lateinit var btnRegistrazione:Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        btnLogin=view.findViewById(R.id.buttonLogin)
        btnRegistrazione=view.findViewById(R.id.buttonRegistrazione)
        btnLogin.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.viewLogin)
        }
        btnRegistrazione.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.viewRegistrazione)
        }
    }
}