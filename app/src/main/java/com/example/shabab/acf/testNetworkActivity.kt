package com.example.shabab.acf

import android.app.Application
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_start_screen.*
import kotlinx.android.synthetic.main.activity_test_network.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class testNetworkActivity : AppCompatActivity() {

    // instantiate an OkHttpClient and create a Request object
    val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_network)
    }


    fun getPetInfo(view: View){
        try {
            val requestedPetName = petRequestEditText.text.toString().toLowerCase()
            //val testPetName = "Cat"

            // fetch JSON to get pet information
            fetchPetInfoJSON(requestedPetName)

            /*
            if(requestedPetName.equals(testPetName))
            {
                val requestNotificationToast = Toast.makeText(this, "Getting pet information...", Toast.LENGTH_SHORT)
                requestNotificationToast.show()
                //petNameTextView.text = basicPetInfo.name
                //petAgeTextView.text = basicPetInfo.age.toString()
                //petWeightTextView.text = basicPetInfo.weight.toString()
                //petFeedTimeTextView.text = basicPetInfo.feed_times[0].toString()

            }
            else
            {
                val requestNotificationToast = Toast.makeText(this, "Could not find pet information...", Toast.LENGTH_SHORT)
                requestNotificationToast.show()

                petNameTextView.text = "Pet Name"
                petAgeTextView.text = "Pet Age"
                petWeightTextView.text = "Pet Weight (lbs)"
                petFeedTimeTextView.text = "Feeding Time"

            }
            */

        }
        catch (e: IOException){
            val JSONFailNotificationToast = Toast.makeText(this, "Could not get pet info :(", Toast.LENGTH_SHORT)
            JSONFailNotificationToast.show()
        }

    }

    fun fetchPetInfoJSON(requestedPetName : String) {


        //val url = "https://api.letsbuildthatapp.com/youtube/home_feed"
        val url = "http://129.21.147.63:5000/pets/name/" + requestedPetName

        // declare a request based on the url
        val request = Request.Builder().url(url).build()

        // use enqueue to request a JSON
        client.newCall(request).enqueue(object: Callback {

            // occurs on response
            override fun onResponse(call: Call?, response: Response?) {
                // get the JSON as a string
                val body = response?.body()?.string()

                // pring the JSON text for debug
                println(body)

                // create a GSON builder to process the JSON message
                val gson = GsonBuilder().create()

                //val homeFeed = gson.fromJson(body, HomeFeed::class.java)
                try{
                    // Process the JSON message into a PetInfo instance
                    val basicPetInfo = gson.fromJson(body, PetInfo::class.java)

                    // ideally want to return this basic pet info
                    runOnUiThread {
                        // for now it will immediately update the text views on the app
                        // this should be removed once the basicPetInfo can be returned

                        if(requestedPetName.equals(basicPetInfo.name))
                        {
                            petNameTextView.text = basicPetInfo.name
                            petAgeTextView.text = basicPetInfo.age.toString()
                            petWeightTextView.text = basicPetInfo.weight.toString() + " lbs"
                            petFeedTimeTextView.text = basicPetInfo.feed_times.toString()
                            //petNameTextView.text = basicPetInfo.name
                            //petAgeTextView.text = basicPetInfo.age.toString()
                            //petWeightTextView.text = basicPetInfo.weight.toString()
                            //petFeedTimeTextView.text = basicPetInfo.feed_times[0].toString()

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

// this will be used for the project
class PetInfo(val name: String, val age: Int, val weight: Int, val feed_times: List<Int>)

// this is for a temporary JSON test
class HomeFeed(val videos: List<Video>)
class Video(val id: Int, val name: String, val link: String, val imageURL: String,
            val numberOfViews: Int, val channel: Channel )
class Channel(val name: String, val profileImageUrl: String)