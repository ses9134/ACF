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
        catch (e: IOException){
            val JSONFailNotificationToast = Toast.makeText(this, "Could not get pet info :(", Toast.LENGTH_SHORT)
            JSONFailNotificationToast.show()
        }
    }

    fun fetchPetInfoJSON(requestedPetName : String)  {
        // instantiate an OkHttpClient and create a Request object
        val client = OkHttpClient()
        //val url = "https://api.letsbuildthatapp.com/youtube/home_feed"
        val url = "http://129.21.145.149:5000/pets/name/" + requestedPetName
        //val url = "http://192.168.10.1:5000/pets/name/" + requestedPetName
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

                    // ideally want to return this basic pet info
                    runOnUiThread {
                        if(requestedPetName.equals(basicPetInfo.name) || requestedPetName.toLowerCase().equals(basicPetInfo.name))
                        {
                            petNameTextView.text = basicPetInfo.name
                            petAgeTextView.text = basicPetInfo.age.toString()
                            petWeightTextView.text = basicPetInfo.weight.toString() + " lbs"
                            petFeedTimeTextView.text = basicPetInfo.feed_times[0].toString()
                        }
                        else
                        {
                            petNameTextView.text = "Pet Name"
                            petAgeTextView.text = "Pet Age"
                            petWeightTextView.text = "Pet Weight (lbs)"
                            petFeedTimeTextView.text = "Feeding Time"
                        }

                    }
                }catch (e: Exception)
                {

                }
            }
            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request")
            }
        })

    }
}



// this is for a temporary JSON test
class HomeFeed(val videos: List<Video>)
class Video(val id: Int, val name: String, val link: String, val imageURL: String,
            val numberOfViews: Int, val channel: Channel )
class Channel(val name: String, val profileImageUrl: String)

