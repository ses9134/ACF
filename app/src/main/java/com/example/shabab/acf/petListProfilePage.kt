package com.example.shabab.acf

import android.content.Context
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_pet_list_profile_page.*

class petListProfilePage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_list_profile_page)
        try{
            println("PETLISTGOOGOGOGOGOG")
            println(petListJSONString)
            val gson = GsonBuilder().create()
            var currentPets = gson.fromJson(petListJSONString, PetList::class.java)
            println("Pet List Received")
            catNamelistView.adapter = CatNameAdapter(this, currentPets)
            catShortFeedingSchedulelistView.adapter = ShortFeedScheduleAdapter(this, currentPets)


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
            return "TEST STRING CAT NAME ADAPTER"
        }

        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val textView = TextView(mContext)
            textView.text = mPetList.petlist[position].name
            return textView
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
            val textView = TextView(mContext)
            textView.text = mPetList.petlist[position].name
            return textView
        }
    }



}
