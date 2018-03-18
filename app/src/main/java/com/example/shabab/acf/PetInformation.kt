package com.example.shabab.acf

import android.media.MediaPlayer
import android.view.View

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

// temporary petInfo object for debug --> NEEDS TO BE UPDATED
class PetInfo(val name: String, val age: String, val weight: String, val feed_times: Array<String>)

// Pet object that should be used for final project
class Pet(val name: String, val age: Int, val feed_times: Array<FeedTime>, val feederID: Int)
// feed time that will be used for final project
class FeedTime(val day: Char, val time: String, val petServingSize: Float)

