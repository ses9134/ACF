package com.example.shabab.acf

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_start_screen.*

class StartScreen : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)
    }


    fun launchTestNetworkActivity(view: View) {
        // Create an Intent to start the TestNetwork Activity
        val testNetworkIntent = Intent(this, testNetworkActivity::class.java)
        // Start the TestNetwork Activity
        startActivity(testNetworkIntent)
    }


}
