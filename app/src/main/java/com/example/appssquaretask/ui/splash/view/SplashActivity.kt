package com.example.appssquaretask.ui.splash.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.appssquaretask.R
import com.example.appssquaretask.ui.map.view.MapActivity

class SplashActivity : AppCompatActivity() {
    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 5000 //5 seconds

    internal val mRunnable: Runnable = Runnable {

                val intent = Intent(applicationContext, MapActivity::class.java)
                startActivity(intent)

            finish()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        startHandler()
    }
    fun startHandler()
    {

        mDelayHandler = Handler()
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)
    }
}
