package com.example.shabab.acf

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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

    // UPDATE THIS METHOD IN THE EVENT THAT A PET IS BEING UPDATED
    fun addPetToDatabase(view: View)
    {
        var petName = petNameEditText.text.toString()
        var petAge = petAgeEditText.text.toString().toInt()
        var petWeight = petWeightEditText.text.toString().toInt()
        var petFeedTimes = buildEmptyFeedTime()
        var petServSize = 0.toFloat()
        var petFeederID = 0
        var petTagID = "0000000"
        //fun initPet(name : String, age : Int, weight : Int, food_quantity: Float, feed_times : FeedTime, feeder_id: Int, tag_id: String) : Pet{
        val pet = initPet(petName, petAge, petWeight, petServSize, petFeedTimes, petFeederID, petTagID)
        UpdatePet(pet)
    }

    fun launchPetListProfilePage() {
        // make the request to get all pets
        getAllPets()
        println(petListJSONString)

        if (canContinue == 1)
        {
            canContinue = 0
            // Create an Intent to start the TestNetwork Activity
            val petProfileListIntent = Intent(this, petListProfilePage::class.java)
            // Start the TestNetwork Activity
            startActivity(petProfileListIntent)
        }
        else if (canContinue == 0)
        {
            val confirmationToast = Toast.makeText(this, "Select button again to confirm", Toast.LENGTH_SHORT)
            confirmationToast.show()
        }
        else
        {
            val errorToast = Toast.makeText(this, "Did not receive all pets. Try Again.", Toast.LENGTH_SHORT)
            errorToast.show()
        }
    }

}
