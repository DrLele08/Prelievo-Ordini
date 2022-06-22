package it.drlele08.prelievoordini.view.registrazione

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.Navigation
import it.drlele08.prelievoordini.R
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class RegistrazioneFragment : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_registrazione, container, false)
    }
    private lateinit var btnContinua:Button
    private lateinit var inputNome:EditText
    private lateinit var inputIdent:EditText
    private lateinit var inputEmail:EditText
    private lateinit var inputCell:EditText
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        btnContinua=view.findViewById(R.id.btnSecondSignUp)
        inputNome=view.findViewById(R.id.inputRegNome)
        inputIdent=view.findViewById(R.id.inputRegIden)
        inputEmail=view.findViewById(R.id.inputRegEmail)
        inputCell=view.findViewById(R.id.inputRegCell)
        btnContinua.setOnClickListener{
            val nome=inputNome.text.toString().trim()
            val identi=inputIdent.text.toString().trim()
            val email=inputEmail.text.toString().trim()
            val cell=inputCell.text.toString().trim()
            if(nome.isNotEmpty() && identi.isNotEmpty() && email.isNotEmpty() && cell.isNotEmpty() && email.contains("@"))
            {
                val bundle=Bundle()
                bundle.putString("nome",nome)
                bundle.putString("identificativo",identi)
                bundle.putString("email",email)
                bundle.putString("cell",cell)
                Navigation.findNavController(view).navigate(R.id.registrazioneContinuoFragment,bundle)
            }
            else
            {
                MotionToast.darkToast(requireActivity(),getString(R.string.attenzione),getString(R.string.ins_tut_camp),
                    MotionToastStyle.WARNING,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
            }
        }
    }
}