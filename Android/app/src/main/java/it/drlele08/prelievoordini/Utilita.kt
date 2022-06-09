package it.drlele08.prelievoordini

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import it.drlele08.prelievoordini.model.Utente

class Utilita
{
    /*
        Librerie Utilizzate:
        Toast: https://github.com/Spikeysanju/MotionToast
        Dialog: https://github.com/afollestad/material-dialogs
        Animation: https://github.com/airbnb/lottie-android
        Image Download: https://github.com/bumptech/glide
        Image Picker: https://github.com/Drjacky/ImagePicker
        Text Recognition: https://developers.google.com/ml-kit/vision/text-recognition/android
        Barcode Scanner: https://developers.google.com/ml-kit/vision/barcode-scanning/android
        Stepper: https://github.com/kojofosu/Quantitizer
        Gson: https://github.com/google/gson
     */
    companion object
    {
        const val host="http://192.168.2.132:8000"
        //const val host="https://www.tesi.saisraffaele.it"
        const val token="123456"
        const val oneSignalKey = "2fef20cf-7fae-43f0-b50e-3c52f309dbfa"
        var user: Utente? = null

        fun saveDato(context:Context,key:String,value:String)
        {
            val sharedPreference=context.getSharedPreferences("APP_PRELIEVI",Context.MODE_PRIVATE)
            val editor = sharedPreference.edit()
            editor.putString(key,value)
            editor.apply()
        }

        fun getDato(context:Context,key:String):String
        {
            val sharedPreference=context.getSharedPreferences("APP_PRELIEVI",Context.MODE_PRIVATE)
            return sharedPreference.getString(key,"")!!
        }

        fun setProgressDialog(context:Context, message:String): AlertDialog
        {
            val llPadding = 30
            val ll = LinearLayout(context)
            ll.orientation = LinearLayout.HORIZONTAL
            ll.setPadding(llPadding, llPadding, llPadding, llPadding)
            ll.gravity = Gravity.CENTER
            var llParam = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            llParam.gravity = Gravity.CENTER
            ll.layoutParams = llParam

            val progressBar = ProgressBar(context)
            progressBar.isIndeterminate = true
            progressBar.setPadding(0, 0, llPadding, 0)
            progressBar.layoutParams = llParam

            llParam = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            llParam.gravity = Gravity.CENTER
            val tvText = TextView(context)
            tvText.text = message
            tvText.setTextColor(Color.parseColor("#000000"))
            tvText.textSize = 20.toFloat()
            tvText.layoutParams = llParam

            ll.addView(progressBar)
            ll.addView(tvText)

            val builder = AlertDialog.Builder(context)
            builder.setCancelable(true)
            builder.setView(ll)

            val dialog = builder.create()
            val window = dialog.window
            if (window != null) {
                val layoutParams = WindowManager.LayoutParams()
                layoutParams.copyFrom(dialog.window?.attributes)
                layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
                dialog.window?.attributes = layoutParams
            }
            return dialog
        }
    }

}