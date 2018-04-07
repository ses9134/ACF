package com.example.shabab.acf

import android.media.MediaPlayer
import android.view.View
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


// this is the IP Address of the ACF unit --> will be modified when connecting to a feeder unit
//val ACF_IP_ADDRESS = "129.21.146.150:5000"
val ACF_IP_ADDRESS = "192.168.10.1:5000"

// this function is used to initialize a Pet
fun initPet(petName : String, petAge : String, petWeight : String, petFeedTime : String) : PetInfo{
    // ignore the val jsonString --> NO LONGER NEEDED --> USES GSON Library to create JSON from pet object
    // val jsonString =  "{'name':'" + petName + "', 'age':" + petAge + ", 'weight':"+ petWeight + ", 'feed_times':[]}"
    return PetInfo(petName, petAge, petWeight, Array( 1, {petFeedTime} ))
}

// temporary petInfo object for debug --> NEEDS TO BE UPDATED
class PetInfo(val name: String, val age: String, val weight: String, val feed_times: Array<String>)

// Pet object that should be used for final project
class Pet(val name: String, val age: Int, val feed_times: Array<FeedTime>, val feederID: Int)
// feed time that will be used for final project
class FeedTime(val day: Char, val time: String, val petServingSize: Float)

