package it.drlele08.prelivoordini

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.onesignal.OneSignal

class MainActivity : AppCompatActivity()
{
    private val idOneSignal = "2fef20cf-7fae-43f0-b50e-3c52f309dbfa"

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(idOneSignal)
    }
}