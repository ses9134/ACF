package com.example.shabab.acf

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_add_pet_page.*
import kotlinx.android.synthetic.main.activity_test_network.*
import okhttp3.*
import java.io.IOException

class addPetPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pet_page)
    }

    fun addPetToDatabase(view: View){

        var petName = petNameEditText.text.toString()
        var petAge = petAgeEditText.text.toString()
        var petWeight = petWeightEditText.text.toString()
        var petFeedTime = "2304"

        var petInfoJSONBody = createPetInfoJSONBody(petName, petAge, petWeight, petFeedTime)


        // instantiate an OkHttpClient and create a Request object
        val JSON = MediaType.parse("application/json")

        val body = RequestBody.create(JSON, petInfoJSONBody)
        body.contentType()
        val client = OkHttpClient()

        //val url = "http://129.21.145.159:5000/pets/name/" + petName
        val url = "http://192.168.10.1:5000/pets/name/" + petName
        //val url = "http://192.168.10.1:12345"

        // request a post message
        val request = Request.Builder().url(url).post(body).build()

        // use enqueue to request a JSON
        client.newCall(request).enqueue(object: Callback {

            // occurs on response
            override fun onResponse(call: Call?, response: Response?) {
                // get the JSON as a string
                val body = response?.body()?.string()
                println("PET INFO SENT!")

            }
            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request")
            }
        })


    }

    fun createPetInfoJSONBody(petName : String, petAge : String, petWeight : String, petFeedTime : String) : String{
        return "{'name':'" + petName + "', 'age':" + petAge + ", 'weight':"+ petWeight + ", 'feed_times':[]}"
    }
}

//class PetInfo(val name: String, val age: String, val weight: String, val feed_times: List<String>)
