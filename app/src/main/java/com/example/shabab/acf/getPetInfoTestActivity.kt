package com.example.shabab.acf

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.shabab.acf.R.id.*
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException
import kotlinx.android.synthetic.main.activity_get_pet_info_test.*
import okhttp3.*

class getPetInfoTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_pet_info_test)
    }
    fun getPetInfo(view: View){
        try {
            val requestedPetName = petRequestEditText.text.toString()
            // fetch JSON to get pet information
            fetchPetInfoJSON(requestedPetName)
        }
        catch (e: Exception){
            val JSONFailNotificationToast = Toast.makeText(this, "Could not get pet info :(", Toast.LENGTH_SHORT)
            JSONFailNotificationToast.show()
        }
    }

    fun deletePetInfo(view: View){
        val requestedPetName = petRequestEditText.text.toString()
        deletePet(requestedPetName)
    }


    fun fetchPetInfoJSON(requestedPetName : String) {
        getPet(requestedPetName)
        val gson = GsonBuilder().create()
            try{
                // Process the JSON message into a PetInfo instance
                val basicPetInfo = gson.fromJson(petJSONString, Pet::class.java)
                if(requestedPetName.equals(basicPetInfo.name) || requestedPetName.toLowerCase().equals(basicPetInfo.name))
                {
                    println(basicPetInfo.name)
                    println(basicPetInfo.age.toString())
                    println(basicPetInfo.weight.toString())

                    petNameTextView.text = basicPetInfo.name
                    petAgeTextView.text = basicPetInfo.age.toString()
                    petWeightTextView.text = basicPetInfo.weight.toString()
                    //petFeedTimeTextView.text = basicPetInfo.feed_times.toString()
                }
                else
                {
                    petNameTextView.text = "Pet Name"
                    petAgeTextView.text = "Pet Age"
                    petWeightTextView.text = "Pet Weight"
                    petFeedTimeTextView.text = "Feeding Time"
                }
            } catch (e: Exception) {
                println("ERROR in fetchPetInfoJSON.")
                println(e)
            }
    }
}