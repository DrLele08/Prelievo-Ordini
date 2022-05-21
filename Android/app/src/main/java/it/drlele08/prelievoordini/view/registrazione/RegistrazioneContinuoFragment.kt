package it.drlele08.prelievoordini.view.registrazione

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.onesignal.OneSignal
import it.drlele08.prelievoordini.MainActivity
import it.drlele08.prelievoordini.R
import it.drlele08.prelievoordini.Utilita
import it.drlele08.prelievoordini.controller.UtenteController
import it.drlele08.prelievoordini.model.Utente
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class RegistrazioneContinuoFragment : Fragment()
{
    private fun doSignUp(nome:String,email:String,pwd:String,identificativo:String,cell:String)
    {
        val progress=Utilita.setProgressDialog(requireContext(),"Registrazione in corso...")
        progress.show()
        UtenteController().doRegistrazione(nome,email,pwd,identificativo,cell,queue, { u ->
            progress.hide()
            Utilita.user=u
            OneSignal.setExternalUserId(u.getEmail())
            Utilita.saveDato(requireContext(), Utente.keyIdUtente,""+u.getIdUtente())
            Utilita.saveDato(requireContext(), Utente.keyTokenUtente,u.getTokenAuth())
            val intent= Intent(requireActivity(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        },{mess ->
            progress.hide()
            MotionToast.darkToast(requireActivity(),"Attenzione",mess,
                MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_registrazione_continuo, container, false)
    }

    private lateinit var inputPass:EditText
    private lateinit var inputRepeatPass:EditText
    private lateinit var btnRegistra:Button
    private lateinit var queue:RequestQueue
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        val nome=arguments?.getString("nome","")!!
        val ident=arguments?.getString("identificativo","")!!
        val email=arguments?.getString("email","")!!
        val cell=arguments?.getString("cell","")!!
        queue=Volley.newRequestQueue(requireContext())
        inputPass=view.findViewById(R.id.inputRegPwd)
        inputRepeatPass=view.findViewById(R.id.inputRegPwdRep)
        btnRegistra=view.findViewById(R.id.btnSecondSignUp2)

        btnRegistra.setOnClickListener{
            val pwd=inputPass.text.toString().trim()
            val pwdRepeat=inputRepeatPass.text.toString().trim()
            if(pwd.isNotEmpty() && pwdRepeat.isNotEmpty())
            {
                if(pwd==pwdRepeat)
                {
                    doSignUp(nome,email,pwd,ident,cell)
                }
                else
                {
                    MotionToast.darkToast(requireActivity(),"Attenzione","Le password non coincidono",
                        MotionToastStyle.WARNING,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
                }
            }
            else
            {
                MotionToast.darkToast(requireActivity(),"Attenzione","Inserisci tutti i campi",
                    MotionToastStyle.WARNING,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
            }
        }
    }
}