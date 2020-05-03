package com.example.flame.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.flame.TAG
import com.google.firebase.Timestamp
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

class HabitsRepo {
    //reference to database
    val db = Firebase.firestore

    //habit list
    val habitList = MutableLiveData<List<Habit>>()

    init{
        //add snapshot listener to database for when habits are added or removed
        db.collection("habits").addSnapshotListener{snapshot, error ->
            if(error != null){
                //handle error
                Log.i(TAG, "Firebase listen failed", error)
                return@addSnapshotListener
            }
            if(snapshot != null){
                parseAllHabits(snapshot)
            } else {
                Log.w(TAG, "Data is null")
            }
        }
    }

    //function for refreshing habit data whenever database changes
    private fun parseAllHabits(result: QuerySnapshot){
        var allHabits = mutableListOf<Habit>()

        for(habit in result){
            val id: String = habit.id //id to be used for deleting habit later if desired
            val name = habit.getString("name")!!
            val category = habit.getString("category")!!
            val color = habit.getString("color")!!
            val dateActivated = (habit.get("dateActivated") as Timestamp).toDate()

            val currentDate = Date()
            val diffInMillies = abs(currentDate.time - dateActivated.time)
            val diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS)
            Log.i(TAG, "id: ${id}, name: ${name}, category: ${category}, color: ${color}, dateActivated: ${dateActivated}, time between date started and now: ${diff}")
            allHabits.add(
                Habit(
                id,
                name,
                category,
                color,
                dateActivated,
                diff.toInt()
            ))
        }

        //update habit list
        habitList.value = allHabits
    }

    fun addHabit(habit: Habit){
        //fill in habit data
        val habitToAdd = hashMapOf(
            "name" to habit.name,
            "category" to habit.category,
            "color" to habit.color,
            "dateActivated" to habit.dateActivated
        )

        //add habit to database
        db.collection("habits")
            .add(habitToAdd)
            .addOnSuccessListener {
                Log.i(TAG, "DocumentSnapshot added with ID: ${it.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    //function to delete habit from Firebase database
    fun deleteHabit(id: String){
        db.collection("habits").document(id).delete()
    }
}