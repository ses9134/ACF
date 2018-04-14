package com.example.shabab.acf

import android.content.Context
import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.TextView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_pet_list_profile_page.*
import kotlinx.android.synthetic.main.cat_name_row.view.*
import kotlinx.android.synthetic.main.feed_time_short_view.view.*

class petListProfilePage : AppCompatActivity() {

    var catname = " "
    var age = 0
    var weight = 0
    var servSize = 0
    var feederID = 0
    var RFID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_list_profile_page)
        try{
            println("PETLISTGOOGOGOGOGOG")
            println(petListJSONString)
            var currentPets = buildListOfPets()
            println("Pet List Received")
            catNamelistView.adapter = CatNameAdapter(this, currentPets)
            catShortFeedingSchedulelistView.adapter = ShortFeedScheduleAdapter(this, currentPets)

            // Set an item click listener for ListView
            catNamelistView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                // Get the selected item text from ListView
                val selectedItem = parent.getItemAtPosition(position) as String
                val selectedCat = catNamelistView.adapter.getItem(position) as Pet
                catNameShortTextView.text = selectedCat.name
                catAgeShortTextView.text = selectedCat.age.toString()
                catWeightShortTextView.text = selectedCat.weight.toString()
                catServSizeShortTextView.text = selectedCat.food_quantity.toString()
                catFeederShortTextView.text = 0.toString()
                catRFIDShortTextView.text = 124.toString()
            }
        }
        catch(e: Exception)
        {
            println("ERROR in petListProfilePage in onCreate()")
            println(e)
            catNamelistView.visibility = View.INVISIBLE
            catShortFeedingSchedulelistView.visibility = View.INVISIBLE
        }
    }

    private class CatNameAdapter(context: Context, petList: PetList): BaseAdapter() {

        private val mContext: Context
        private val mPetList: PetList
        init {
            mContext = context
            mPetList = petList
        }

        // responsible for how many rows in the list
        // The number of rows is the number of pets in the list of all the pets
        override fun getCount(): Int {
            return mPetList.petlist.count()
        }

        //
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            return mPetList.petlist[position]
        }

        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val catNameRow = layoutInflater.inflate(R.layout.cat_name_row, viewGroup, false)
            catNameRow.position_cat_name_textview.text = mPetList.petlist[position].name
            return catNameRow

        }
    }

    private class ShortFeedScheduleAdapter(context: Context, petList: PetList): BaseAdapter() {

        private val mContext: Context
        private val mPetList: PetList
        init {
            mContext = context
            mPetList = petList
        }

        // responsible for how many rows in the list
        // The number of rows is the number of pets in the list of all the pets
        override fun getCount(): Int {
            return mPetList.petlist.count()
        }

        //
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            return "TEST STRING CAT NAME ADAPTER"
        }

        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val catFeedScheduleRow = layoutInflater.inflate(R.layout.feed_time_short_view, viewGroup, false)
            //catFeedScheduleRow.startTimeTextView.text = mPetList.petlist[position].feed_times.F[[position][position]].toString()
            return catFeedScheduleRow
        }
    }

    // this launches the add pet activity
    fun launchAddPetPageActivity(view: View) {
        // Create an Intent to start the Add Pet Page Activity
        val addPetIntent = Intent(this, addPetPage::class.java)
        // Start the AddPetPage Activity
        startActivity(addPetIntent)
    }

}
