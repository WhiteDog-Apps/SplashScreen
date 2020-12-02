package com.whitedog.example_splashscreen

import android.content.Intent
import android.os.Bundle
import com.whitedog.splashscreen.SplashScreenActivity

class MainActivity : SplashScreenActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startSplashTask(2500)
    }

    override fun doInBackgroundSplashTask() {
        Thread.sleep(3500)
    }

    override fun onSplashTaskFinished() {
        startActivity(Intent(this, SecondActivity::class.java))

        finish()
    }

}