package com.example.flame.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HabitsRepo {
    //reference to database
    val db = Firebase.firestore

    //habit list
    val habitList = MutableLiveData<List<Habit>>()

    init{
        //add snapshot listener to database for when habits are added or removed
        db.collection("movies").addSnapshotListener{snapshot, error ->
            if(error != null){
                //handle error
                Log.i("movies", "Firebase listen failed", error)
                return@addSnapshotListener
            }
            if(snapshot != null){
                parseAllMovies(snapshot)
            } else {
                Log.w("movies", "Data is null")
            }
        }
    }
}