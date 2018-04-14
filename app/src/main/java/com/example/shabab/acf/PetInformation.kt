package com.example.shabab.acf

import android.media.MediaPlayer
import android.util.Log.e
import android.view.View
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import java.util.logging.Logger

/**
 * Created by Shabab on 3/5/2018.
 *
 * File: PetInformation.kt
 * Created by: Shabab Siddiq
 * Date: 3/5/2018
 * Description: This file contains the class information that
 *              will be used to create pet objects and feed time
 *              objects that will be used for the ACF Application
 */
// this will be used for the project

var petListJSONString = " "
var petJSONString = " "
// canContinue = 0 --> false
// canContinue = 1 --> true
// canContinue = 2 --> error
var canContinue = 0

// this is the IP Address of the ACF unit --> will be modified when connecting to a feeder unit
//val ACF_IP_ADDRESS = "192.168.10.1:5000"
val ACF_IP_ADDRESS = "129.21.146.118:5000"

// this function is used to initialize a Pet
fun initPet(name : String, age : Int, weight : Int, food_quantity: Float, feed_times : FeedTime, feeder_id: Int, tag_id: String) : Pet{
    return Pet(name, age, weight, food_quantity, feed_times, feeder_id, tag_id)
}

fun buildEmptyFeedTime() : FeedTime{
    var emptyPair = Pair(0,0)
    var emptyList = listOf<Pair<Int,Int>>(emptyPair)
    return FeedTime(emptyList, emptyList, emptyList, emptyList, emptyList, emptyList, emptyList)
}

//class Pet(val name: String, val age: Int, val weight: Int, val food_quantity: Float)
// Pet object that should be used for final project
class PetList(val petlist: List<Pet>)
class Pet(val name: String, val age: Int, val weight: Int, val food_quantity: Float, val feed_times: FeedTime, val feeder_id: Int, val tag_id : String)
// feed time that will be used for final project
// U - Sunday, M - Monday, T - Tuesday, W - Wednesday, R - Thursday, F - Friday, S - Saturday
class FeedTime(val U: List<Pair<Int, Int>>, val M: List<Pair<Int, Int>>, val T: List<Pair<Int, Int>>, val W: List<Pair<Int, Int>>, val R: List<Pair<Int, Int>>, val F: List<Pair<Int, Int>>, val S: List<Pair<Int, Int>>)


//class FeedTime(val U: List<FeedWindow>, val M: List<FeedWindow>, val T: List<FeedWindow>, val W: List<List<FeedWindow>>, val R: List<FeedWindow>, val F: List<FeedWindow>, val S: List<FeedWindow>)
// "feed_times": {"M": [ ], "W": [[0, 2400]], "S": [], "F": [], "R": [[0, 2400]], "U": [], "T": [[0, 2400]]}
//class FeedWindow( val StartTime: Int, val EndTime: Int )
//class StartTime( val StartTime: Int)
//class EndTime(val EndTime: Int)
//class FeedWindow( List<FeedTimeFrame> )

//{"petlist":
// [
// {"feeder_id": 1, "name": "sniffles", "food_quantity": 5.5, "weight": 25, "feed_times": {"M": [ ], "W": [[0, 2400]], "S": [], "F": [], "R": [[0, 2400]], "U": [], "T": [[0, 2400]]}, "db_id": {"$oid": "5a7e1f371561fd1435763fe5"}, "tag_id": "0x736e6966666c658cffffffffffffffff", "age": 5},
// {"feeder_id": 0, "name": "fluff", "food_quantity": 50, "weight": 15, "feed_times": {"M": [], "W": [], "S": [], "F": [], "R": [], "U": [], "T": []}, "tag_id": "0x666c756675ffffffffffffffffffffff", "age": 2},
// {"feeder_id": 0, "name": "mango", "food_quantity": 50, "feed_times": {"M": [], "W": [], "S": [], "F": [], "R": [], "U": [], "T": []}, "weight": 12, "age": 4}
// ]
// }

// The following are the methods used to get data to/from the database
/**
 * Created by Shabab on 4/10/2018.
 * Method: buildListOfPets
 * Description: This method returns a pet list from the JSON string received from the pi
 */
fun buildListOfPets() : PetList{
    val gson = GsonBuilder().create()
    var currentPets = gson.fromJson(petListJSONString, PetList::class.java)
    return currentPets
}

// The following are the methods used to get data to/from the database
/**
 * Created by Shabab on 4/10/2018.
 * Method: getAllPets
 * Description: This method adds a pet using the pet object
 *              If the pet doesn't already exist, it gets added
 *              to the database.
 *              If the pet already exists, it updates the existing
 *              pet in the database
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
                petListJSONString = body.toString()
                canContinue = 1
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

/**
 * Created by Shabab on 4/10/2018.
 * Method: UpdatePet
 * Description: This method adds a pet using the pet object
 *              If the pet doesn't already exist, it gets added
 *              to the database.
 *              If the pet already exists, it updates the existing
 *              pet in the database
 */
fun UpdatePet(pet: Pet){

    val gson  = GsonBuilder().setPrettyPrinting().create()
    val jsonPet: String = gson.toJson(pet)
    // instantiate an OkHttpClient and create a Request object
    //val JSON = MediaType.parse("application/json; charset=us-ascii")
    val JSON = MediaType.parse("application/json")
    val client = OkHttpClient()
    val body = RequestBody.create(JSON, jsonPet.toString())
    val url = "http://" + ACF_IP_ADDRESS + "/pets/name/" + pet.name

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
            println("PET INFO SENT/UPDATED!")
            println(body)
        }
        override fun onFailure(call: Call?, e: IOException?) {
            println("Failed to execute request:  UPDATE PET")
        }
    })
}

/**
 * Created by Shabab on 4/10/2018.
 * Method: deletePet
 * Description: This method removes a pet from the database
 */
fun deletePet(petToDelete: String){
    try {
        // instantiate an OkHttpClient and create a Request object
        val client = OkHttpClient()
        val url = "http://" + ACF_IP_ADDRESS +  "/pets/name/" + petToDelete
        // declare a request based on the url
        val request = Request.Builder().url(url).delete().build()

        // use enqueue to request a JSON
        client.newCall(request).enqueue(object: Callback {

            // occurs on response
            override fun onResponse(call: Call?, response: Response?) {
                // get the JSON as a string
                println("Removed " + petToDelete + " from ACF")
            }
            override fun onFailure(call: Call?, e: IOException?) {
            }
        })
    }
    catch (e: Exception){
        println("Could not delete " + petToDelete)
    }
}

/**
 * Created by Shabab on 4/10/2018.
 * Method: getPet
 * Description: This method gets the info for a specific a pet from the database
 */
fun getPet(requestedPetName : String){
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
            // create a GSON builder to process the JSON message
            val gson = GsonBuilder().create()
            try{
                petJSONString = body.toString()
            }catch (e: Exception) {
                println("ERROR in getPet.")
                println(e)
            }
        }
        override fun onFailure(call: Call?, e: IOException?) {
        }
    })
}

// acfTimeInt object
// timeInt -> time as a strimg
// AMorPM --> 0 for AM, 1 for PM
class ACFTimeInt(val timeInt: Int, val AMorPM: Int)

// acfTimeString objectss
// timeRawString -> time as a raw strimg (ie 2304 for 11:04PM)
// AMorPM --> 0 for AM, 1 for PM
// timeFormattedString --> formatted string for display in UI (ie 11:04PM, 10:00AM)
class ACFTimeString(val timeRawString: String, val AMorPM: Int, val timeFormattedString : String)

/**
 * Created by Shabab on 4/10/2018.
 * Method: TimeIntToACFTimeString
 * Description: This method converts time from an Int to ACFTimeString object
 *              Converts from Military time to regular time (ie 2304 = 11:04PM)
 */
fun TimeIntToACFTimeString(timeInt : Int) : ACFTimeString {
    // convert the current integer to a string
    var timeRawString = timeInt.toString()
    var timeFormattedString = " "
    var timeAMorPM = 0
    var timeStringHour = " "
    var timeStringMinute = " "

    // if the integer is less than 1000 (aka 12AM ro 9:59AM
    if (timeInt < 1000){
        // get the time hour and minute as strings
        timeStringHour = timeRawString[0].toString()
        timeStringMinute = timeRawString[1].toString() + timeRawString[2].toString()
    }
    // else the integer is more than 1000 (aka 10:00AM
    else{
        // get the time hour and minute as strings
        timeStringHour = timeRawString[0].toString() + timeRawString[1].toString()
        timeStringMinute = timeRawString[2].toString() + timeRawString[3].toString()
    }

    // convert the hours and minutes to Integers
    var timeIntHour = timeStringHour.toInt()
    var timeIntMinute = timeStringMinute.toInt()

    // if the hour is in the range 0-11, then it is an AM time
    if(timeIntHour >= 0 || timeIntHour <= 11){
        // assign the time to be an AM
        timeAMorPM = 0
    }
    // if the time is noon
    else if (timeInt == 12) {
        // assign the time to be PM
        timeAMorPM = 1
    }
    // else the hour is a PM time if after noon
    else if (timeInt > 12){
        // convert the time to be a PM
        timeIntHour -= 12
        timeAMorPM = 1
    }

    timeRawString = (timeIntHour*100 + timeIntMinute).toString()
    if(timeAMorPM == 0)
    {
        timeFormattedString = (timeIntHour*100).toString() + ":" + timeIntMinute.toString() + "AM"
    }
    else{
        timeFormattedString = (timeIntHour*100).toString() + ":" + timeIntMinute.toString() + "PM"
    }

    return ACFTimeString(timeRawString, timeAMorPM, timeFormattedString)
}

/**
 * Created by Shabab on 4/10/2018.
 * Method: ACFTimeStringToTimeInt
 * Description: This method converts time from a String to Int for the pi communication
 *              Converts from regular time to Military time (ie 11:04PM = 2304)
 */
fun ACFTimeStringToTimeInt(acfTimeString: ACFTimeString) : Int {
    var timeStringHour = " "
    var timeStringMinute = " "
    var timeIntHour = 0
    var timeIntMinute = 0
    var timeInt = acfTimeString.timeRawString.toInt()
    // if the integer is less than 1000 (aka 12AM ro 9:59AM
    if (timeInt < 1000){
        // get the time hour and minute as strings
        timeStringHour = acfTimeString.timeRawString[0].toString()
        timeStringMinute = acfTimeString.timeRawString[1].toString() + acfTimeString.timeRawString[2].toString()
        timeIntHour = timeStringHour.toInt()
        timeIntMinute = timeStringMinute.toInt()
    }
    // else the integer is more than 1000 (aka 10:00AM
    else{
        // get the time hour and minute as strings
        timeStringHour = acfTimeString.timeRawString[0].toString() + acfTimeString.timeRawString[1].toString()
        timeStringMinute = acfTimeString.timeRawString[2].toString() + acfTimeString.timeRawString[3].toString()
        timeIntHour = timeStringHour.toInt()
        timeIntMinute = timeStringMinute.toInt()
    }

    // if the time is a PM time
    if (acfTimeString.AMorPM == 1){
        // if the time is noon
        if (timeIntHour == 12)
        {
            // do nothing. This is fine. :)
        }
        // else the time must be after noon
        else{
            // add 12 to make it military time
            timeIntHour += 12
        }
    }
    // else the time is an AM time
    else{
        // do nothing. This is fine. :)
    }

    var newTimeInt = timeIntHour*100 + timeIntMinute
    return newTimeInt
}