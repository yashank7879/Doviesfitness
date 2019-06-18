package com.doviesfitness

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import com.doviesfitness.ui.authentication.IntroSliderActivity

class SplashScreenActivity : AppCompatActivity() {
    var mRunnable: Runnable? = null
    val mHandlers = Handler()
    val DELAY : Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash_screen)

        mRunnable = Runnable{
            intent = Intent(this@SplashScreenActivity, IntroSliderActivity::class.java)
            startActivity(intent)
            finish()
        }

        mHandlers.postDelayed(mRunnable,DELAY)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mHandlers.removeCallbacks(mRunnable)
    }
}
