package com.example.shabab.acf

import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_connect_to_feeder.*
import kotlinx.android.synthetic.main.activity_get_pet_info_test.*
import okhttp3.*
import java.io.IOException

class connectToFeederActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect_to_feeder)

    }

    fun launchPetListProfilePage(view: View) {
        getPetListInfo()
        // create a GSON builder to process the JSON message
        val gson = GsonBuilder().create()
        val petListJSONString =  petListJSONtextView.text //gson.fromJson(petListJSONtextView.text.toString(), PetInfo::class.java)
        // Create an Intent to start the TestNetwork Activity
        val petProfileListIntent = Intent(this, petListProfilePage::class.java)
        // Start the TestNetwork Activity
        startActivity(petProfileListIntent)
    }


    fun getPetListInfo(){

        // instantiate an OkHttpClient and create a Request object
        val client = OkHttpClient()
        val url = "http://" + ACF_IP_ADDRESS + "/pets/"
        // declare a request based on the url
        val request = Request.Builder().url(url).build()

        // use enqueue to request a JSON
        client.newCall(request).enqueue(object: Callback {

            // occurs on response
            override fun onResponse(call: Call?, response: Response?) {
                // get the JSON as a string
                val body = response?.body()?.string()
                // print the JSON text for debug
                println(body)

                try{

                    runOnUiThread(){
                        petListJSONtextView.text = body.toString()
                    }
                }catch (e: Exception)
                {

                }
            }
            override fun onFailure(call: Call?, e: IOException?) {

            }
        })
    }


    fun playPurrSound(view: View){
        val mp = MediaPlayer.create(this, R.raw.catpurr)
        mp.start()
    }
}
