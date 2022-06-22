package it.drlele08.prelievoordini.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
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


class LoginFragment : Fragment()
{

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    private fun doLogin(email:String, pwd:String)
    {
        val dialog=Utilita.setProgressDialog(requireContext(),getString(R.string.login_in_corso))
        dialog.show()
        val userControl=UtenteController()
        userControl.doLoginByEmail(email,pwd,queue,{utente ->
            dialog.hide()
            Utilita.user=utente
            OneSignal.setExternalUserId(utente.getEmail())
            if(checkRicordami.isChecked)
            {
                Utilita.saveDato(requireContext(),Utente.keyIdUtente,""+utente.getIdUtente())
                Utilita.saveDato(requireContext(),Utente.keyTokenUtente,utente.getTokenAuth())
            }
            val intent=Intent(requireActivity(),MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        },{err ->
            dialog.hide()
            MotionToast.darkToast(requireActivity(),getString(R.string.errore),err,
                MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
        })
    }
    private lateinit var queue: RequestQueue
    private lateinit var inputEmail:EditText
    private lateinit var inputPwd:EditText
    private lateinit var checkRicordami:CheckBox
    private lateinit var btnLogin:Button
    private lateinit var btnRegistra:TextView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        queue=Volley.newRequestQueue(requireContext())
        inputEmail=view.findViewById(R.id.inputEmail)
        inputPwd=view.findViewById(R.id.inputPwdLogin)
        checkRicordami=view.findViewById(R.id.checkRicordami)
        btnLogin=view.findViewById(R.id.btnLoginUser)
        btnRegistra=view.findViewById(R.id.textLoginSignUp)
        btnLogin.setOnClickListener{
            val email=inputEmail.text.toString().trim()
            val pwd=inputPwd.text.toString().trim()
            if(email.isNotEmpty() && pwd.isNotEmpty())
                doLogin(email,pwd)
            else
            {
                MotionToast.darkToast(requireActivity(),getString(R.string.errore),getString(R.string.ins_tut_camp),
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
            }
        }

        btnRegistra.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.viewRegistrazione)
        }
    }
}