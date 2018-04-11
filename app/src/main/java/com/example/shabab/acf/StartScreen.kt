package com.example.shabab.acf

import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_start_screen.*

class StartScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)

    }

    // this launches the test Get Pet Info Test Activity
    fun launchGetPetInfoTestActivity(view: View) {
        // Create an Intent to start the TestNetwork Activity
        val testNetworkIntent = Intent(this, getPetInfoTestActivity::class.java)
        // Start the TestNetwork Activity
        startActivity(testNetworkIntent)
    }

    // this launches the add pet activity
    fun launchAddPetPageActivity(view: View) {
        // Create an Intent to start the Add Pet Page Activity
        val addPetIntent = Intent(this, addPetPage::class.java)
        // Start the AddPetPage Activity
        startActivity(addPetIntent)
    }

    // this launches the initial connection activity
    fun launchConnectToFeederActivity(view: View) {
        // Create an Intent to start the Add Pet Page Activity
        val connectToFeederIntent = Intent(this, connectToFeederActivity::class.java)
        // Start the ConnectToFeeder Activity
        startActivity(connectToFeederIntent)
    }

    fun playMeowSound(view: View){
        val mp = MediaPlayer.create(this, R.raw.catmeow)
        mp.start()
    }


}
