package com.example.shabab.acf

import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class connectToFeederActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect_to_feeder)
    }

    fun playPurrSound(view: View){
        val mp = MediaPlayer.create(this, R.raw.catpurr)
        mp.start()
    }
}
