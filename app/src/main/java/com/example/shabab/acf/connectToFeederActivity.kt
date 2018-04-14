package com.example.shabab.acf

import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.NetworkOnMainThreadException
import android.view.View
import android.widget.Toast
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
        // make the request to get all pets
        getAllPets()
        println(petListJSONString)

        if (canContinue == 0)
        {
            canContinue = 0
            playMeowSound()
            // Create an Intent to start the TestNetwork Activity
            val petProfileListIntent = Intent(this, petListProfilePage::class.java)
            // Start the TestNetwork Activity
            startActivity(petProfileListIntent)
        }
        else if (canContinue == 1)
        {
            playPurrSound()
            val confirmationToast = Toast.makeText(this, "Select button again to confirm", Toast.LENGTH_SHORT)
            confirmationToast.show()
        }
        else
        {
            val errorToast = Toast.makeText(this, "Did not receive all pets. Try Again.", Toast.LENGTH_SHORT)
            errorToast.show()
        }

    }

    /**
     * Created by Shabab on 4/10/2018.
     * Method: getAllPets
     * Description: This method makes a request to get all the pets
     *              from the database
     */
    fun getAllPets(){
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
                //println(body)
                try{
                    runOnUiThread(){
                        petListJSONString = body.toString()
                        canContinue = 1
                    }
                }catch (e: Exception)
                {
                    println("ERROR in getPetListInfo()")
                    println(e)
                }
            }
            override fun onFailure(call: Call?, e: IOException?) {
                canContinue = 2
                println("getPetListInfo onFailure")
                println(e)
            }
        })
    }

    fun playPurrSound(view: View){
        val mp = MediaPlayer.create(this, R.raw.catpurr)
        mp.start()
    }

    fun playPurrSound(){
        val mp = MediaPlayer.create(this, R.raw.catpurr)
        mp.start()
    }

    fun playMeowSound(){
        val mp = MediaPlayer.create(this, R.raw.catmeow)
        mp.start()
    }

}
