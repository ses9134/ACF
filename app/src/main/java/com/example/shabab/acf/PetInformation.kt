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
//val ACF_IP_ADDRESS = "192.168.10.1:5000"
val ACF_IP_ADDRESS = "129.21.146.118:5000"

// this function is used to initialize a Pet
fun initPet(petName : String, petAge : String, petWeight : String, petFeedTime : String) : PetInfo{
    // ignore the val jsonString --> NO LONGER NEEDED --> USES GSON Library to create JSON from pet object
    // val jsonString =  "{'name':'" + petName + "', 'age':" + petAge + ", 'weight':"+ petWeight + ", 'feed_times':[]}"
    return PetInfo(petName, petAge, petWeight, Array( 1, {petFeedTime} ))
}

// temporary petInfo object for debug --> NEEDS TO BE UPDATED
class PetInfo(val name: String, val age: String, val weight: String, val feed_times: Array<String>)

var petListJSONString = " "
// canContinue = 0 --> false
// canContinue = 1 --> true
// canContinue = 2 --> error
var canContinue = 0

//class Pet(val name: String, val age: Int, val weight: Int, val food_quantity: Float)
// Pet object that should be used for final project
class PetList(val petlist: List<Pet>)
class Pet(val name: String, val age: Int, val weight: Int, val food_quantity: Float)//, val feed_times: FeedTime)
// feed time that will be used for final project
// U - Sunday, M - Monday, T - Tuesday, W - Wednesday, R - Thursday, F - Friday, S - Saturday
class FeedTime(val U: List<FeedWindow>, val M: List<FeedWindow>, val T: List<FeedWindow>, val W: List<FeedWindow>, val R: List<FeedWindow>, val F: List<FeedWindow>, val S: List<FeedWindow>)
class FeedWindow( val TimeFrame: List<Int> )
class StartTime( val StartTime: Int)
class EndTime(val EndTime: Int)
//class FeedWindow( List<FeedTimeFrame> )

//{"petlist":
// [
// {"feeder_id": 1, "name": "sniffles", "food_quantity": 5.5, "weight": 25, "_id": {"$oid": "5a7e1f371561fd1435763fe5"}, "feed_times": {"M": [ ], "W": [[0, 2400]], "S": [], "F": [], "R": [[0, 2400]], "U": [], "T": [[0, 2400]]}, "db_id": {"$oid": "5a7e1f371561fd1435763fe5"}, "tag_id": "0x736e6966666c658cffffffffffffffff", "age": 5},
// {"feeder_id": 0, "name": "fluff", "food_quantity": 50, "weight": 15, "_id": {"$oid": "5a7e1f3b1561fd1435763fe6"}, "feed_times": {"M": [], "W": [], "S": [], "F": [], "R": [], "U": [], "T": []}, "tag_id": "0x666c756675ffffffffffffffffffffff", "age": 2},
// {"feeder_id": 0, "name": "mango", "food_quantity": 50, "_id": {"$oid": "5ac39d1c1561fd08b2145513"}, "feed_times": {"M": [], "W": [], "S": [], "F": [], "R": [], "U": [], "T": []}, "weight": 12, "age": 4}
// ]
// }
