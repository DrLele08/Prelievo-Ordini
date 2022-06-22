package it.drlele08.prelievoordini

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.onesignal.OneSignal
import it.drlele08.prelievoordini.controller.UtenteController
import it.drlele08.prelievoordini.databinding.ActivityMainBinding
import it.drlele08.prelievoordini.model.Utente
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class MainActivity : AppCompatActivity()
{

    private lateinit var binding: ActivityMainBinding
    private lateinit var navView:BottomNavigationView
    private lateinit var appBarConfiguration:AppBarConfiguration
    private lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(Utilita.oneSignalKey)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navView = binding.navView
        navController=findNavController(R.id.nav_host_fragment_activity_main)

        val idUtente=Utilita.getDato(this,Utente.keyIdUtente)
        val tokenAuth=Utilita.getDato(this,Utente.keyTokenUtente)
        if(idUtente.isNotEmpty())
        {
            val dialog=Utilita.setProgressDialog(this,getString(R.string.recupera_utente))
            dialog.show()
            val intId=Integer.parseInt(idUtente)
            UtenteController().doLoginByToken(intId,tokenAuth,Volley.newRequestQueue(this),{utente ->
                Utilita.user=utente
                dialog.hide()
                completeSetup()
            },{err->
                dialog.hide()
                MotionToast.darkToast(this,getString(R.string.errore),err,
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular))
                completeSetup()
            })
        }
        else
        {
            completeSetup()
        }
    }

    private fun completeSetup()
    {
        if(Utilita.user != null)
        {
            navView.menu.clear()
            when (Utilita.user!!.getTipoUtente())
            {
                1 ->
                {
                    navView.inflateMenu(R.menu.menu_admin)
                    navController.popBackStack(R.id.viewHome,true)
                    navController.navigate(R.id.viewLetture)
                    appBarConfiguration = AppBarConfiguration(
                        setOf(
                            R.id.viewLetture, R.id.viewProdotti, R.id.viewGestione,R.id.viewImpostazioni
                        )
                    )
                }
                2 ->
                {
                    navView.inflateMenu(R.menu.menu_operatore)
                    navController.popBackStack(R.id.viewHome,true)
                    navController.navigate(R.id.viewLetture)
                    appBarConfiguration = AppBarConfiguration(
                        setOf(
                            R.id.viewLetture, R.id.viewProdotti,R.id.viewImpostazioni
                        )
                    )
                }
                else ->
                {
                    navView.inflateMenu(R.menu.menu_utente)
                    navController.popBackStack(R.id.viewHome,true)
                    navController.navigate(R.id.viewProdotti)
                    appBarConfiguration = AppBarConfiguration(
                        setOf(
                            R.id.viewProdotti,R.id.viewCart,R.id.viewDueIn,R.id.viewPreOrder,R.id.viewImpostazioni
                        )
                    )
                }
            }
        }
        else
        {
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.viewHome, R.id.viewProdotti, R.id.viewDueIn
                )
            )
        }
        navController.addOnDestinationChangedListener { _, nd: NavDestination, _ ->
            supportActionBar!!.show()
            if (nd.id == R.id.viewLogin || nd.id==R.id.detailProdottoFragment || nd.id == R.id.viewRegistrazione || nd.id==R.id.registrazioneContinuoFragment)
            {
                navView.visibility = View.GONE
            }
            else if(nd.id==R.id.letturaSceltaFragment)
            {
                supportActionBar!!.hide()
                navView.visibility = View.GONE
            }
            else
            {
                navView.visibility = View.VISIBLE
            }
        }
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }
    override fun onSupportNavigateUp(): Boolean
    {
        onBackPressed()
        return true
    }
}