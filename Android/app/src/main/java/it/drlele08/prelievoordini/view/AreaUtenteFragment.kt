package it.drlele08.prelievoordini.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import it.drlele08.prelievoordini.MainActivity
import it.drlele08.prelievoordini.R
import it.drlele08.prelievoordini.Utilita
import it.drlele08.prelievoordini.model.Utente


class AreaUtenteFragment : Fragment()
{
    private fun logout()
    {
        MaterialDialog(requireContext()).show {
            title(R.string.logout)
            message(R.string.logoutmess)
            positiveButton(R.string.si) {
                Utilita.user=null
                Utilita.saveDato(requireContext(),Utente.keyIdUtente,"")
                Utilita.saveDato(requireContext(),Utente.keyTokenUtente,"")
                val intent= Intent(requireActivity(), MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            negativeButton(text = "No")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_areautente, container, false)
    }
    private lateinit var btnLogout:Button
    private lateinit var textNome:TextView
    private lateinit var textIdentificativo:TextView
    private lateinit var textEmail:TextView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        btnLogout=view.findViewById(R.id.btnUtenteEsce)
        textNome=view.findViewById(R.id.textUtenteNome)
        textIdentificativo=view.findViewById(R.id.textUtenteIden)
        textEmail=view.findViewById(R.id.textUtenteEmail)
        btnLogout.setOnClickListener{
            logout()
        }
        textNome.text=Utilita.user!!.getNome()
        textIdentificativo.text=Utilita.user!!.getCellulare()
        textEmail.text=Utilita.user!!.getEmail()
    }
}