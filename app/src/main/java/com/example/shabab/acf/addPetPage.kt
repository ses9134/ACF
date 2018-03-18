package com.example.shabab.acf

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_add_pet_page.*
import kotlinx.android.synthetic.main.activity_get_pet_info_test.*
import okhttp3.*
import org.json.JSONObject
import org.xml.sax.Parser
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


        val gson  = GsonBuilder().setPrettyPrinting().create()
        val pet = initPet(petName, petAge, petWeight, petFeedTime)
        val jsonPet: String = gson.toJson(pet)

        // instantiate an OkHttpClient and create a Request object
        //val JSON = MediaType.parse("application/json; charset=us-ascii")
        val JSON = MediaType.parse("application/json")
        val client = OkHttpClient()

        val body = RequestBody.create(JSON, jsonPet.toString())

        val url = "http://129.21.145.149:5000/pets/name/" + petName

        // request a post message
        val request = Request.Builder()
                .url(url)
                .addHeader("Accept", "*/*")
                .post(body)
                .build()

        client.newCall(request).enqueue(object: Callback {

            // occurs on response
            override fun onResponse(call: Call?, response: Response?) {
                // get the JSON as a string
                val body = response?.body()?.string()
                println("PET INFO SENT!")
                println(body)

            }
            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request")
            }
        })
    }

    fun initPet(petName : String, petAge : String, petWeight : String, petFeedTime : String) : PetInfo{
        val jsonString =  "{'name':'" + petName + "', 'age':" + petAge + ", 'weight':"+ petWeight + ", 'feed_times':[]}"
        return PetInfo(petName, petAge, petWeight, Array( 1, {petFeedTime} ))
    }
}

//class PetInfo(val name: String, val age: String, val weight: String, val feed_times: Array<String>)
