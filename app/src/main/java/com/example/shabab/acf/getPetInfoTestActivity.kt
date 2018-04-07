package com.example.shabab.acf

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException
import kotlinx.android.synthetic.main.activity_get_pet_info_test.*
import okhttp3.*
import java.util.logging.Logger


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

            val jsonPet = PetInfo(petNameTextView.text.toString(), petAgeTextView.text.toString(), petWeightTextView.text.toString(), Array(1, {petFeedTimeTextView.text.toString()}))
            val requestedPet = Pet(jsonPet.name, jsonPet.age.toInt(), Array(1, { FeedTime('M', jsonPet.feed_times[0].toString(), 1.5.toFloat()) }), 0)

            println(requestedPet.name)

        }
        catch (e: Exception){
            val JSONFailNotificationToast = Toast.makeText(this, "Could not get pet info :(", Toast.LENGTH_SHORT)
            JSONFailNotificationToast.show()
        }
    }

    fun deletePetInfo(view: View){
        try {
            val requestedPetName = petRequestEditText.text.toString()

            // instantiate an OkHttpClient and create a Request object
            val client = OkHttpClient()
            val url = "http://" + ACF_IP_ADDRESS +  "/pets/name/" + requestedPetName
            // declare a request based on the url
            val request = Request.Builder().url(url).delete().build()

            // use enqueue to request a JSON
            client.newCall(request).enqueue(object: Callback {

                // occurs on response
                override fun onResponse(call: Call?, response: Response?) {
                    // get the JSON as a string

                }
                override fun onFailure(call: Call?, e: IOException?) {
                }
            })
        }
        catch (e: Exception){
            val JSONFailNotificationToast = Toast.makeText(this, "Could not delete pet info :(", Toast.LENGTH_SHORT)
            JSONFailNotificationToast.show()
        }
    }



    fun fetchPetInfoJSON(requestedPetName : String) {

        // instantiate an OkHttpClient and create a Request object
        val client = OkHttpClient()
        val url = "http://" + ACF_IP_ADDRESS +  "/pets/name/" + requestedPetName
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
                // create a GSON builder to process the JSON message
                val gson = GsonBuilder().create()
                try{
                    // Process the JSON message into a PetInfo instance
                    val basicPetInfo = gson.fromJson(body, PetInfo::class.java)


                    runOnUiThread(){
                        if(requestedPetName.equals(basicPetInfo.name) || requestedPetName.toLowerCase().equals(basicPetInfo.name))
                        {
                            println(basicPetInfo.name)
                            println(basicPetInfo.age.toString())
                            println(basicPetInfo.weight.toString())
                            println(basicPetInfo.feed_times.toString())

                            petNameTextView.text = basicPetInfo.name
                            petAgeTextView.text = basicPetInfo.age.toString()
                            petWeightTextView.text = basicPetInfo.weight.toString()
                            petFeedTimeTextView.text = basicPetInfo.feed_times.toString()
                        }
                        else
                        {
                            petNameTextView.text = "Pet Name"
                            petAgeTextView.text = "Pet Age"
                            petWeightTextView.text = "Pet Weight"
                            petFeedTimeTextView.text = "Feeding Time"
                        }
                    }
                }catch (e: Exception)
                {

                }
            }
            override fun onFailure(call: Call?, e: IOException?) {
            }
        })
    }
}





// this is for a temporary JSON test
class HomeFeed(val videos: List<Video>)
class Video(val id: Int, val name: String, val link: String, val imageURL: String,
            val numberOfViews: Int, val channel: Channel )
class Channel(val name: String, val profileImageUrl: String)

