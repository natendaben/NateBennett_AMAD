package com.example.flame.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.flame.TAG
import com.google.firebase.Timestamp
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

class HabitsRepo {
    //reference to database
    private val db = Firebase.firestore

    //habit list
    val habitListOrderedByCategory = MutableLiveData<List<HabitCategory>>()

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
        db.collection("habits").get().addOnSuccessListener { parseAllHabits(it) }
    }

    //function for refreshing habit data whenever database changes
    private fun parseAllHabits(result: QuerySnapshot){
        val allHabits = mutableListOf<HabitCategory>()

        for(habit in result){
            //get data from firebase
            val id: String = habit.id //id to be used for deleting habit later if desired
            val name = habit.getString("name")!!
            val category = habit.getString("category")!!
            val color = habit.getString("color")!!
            val dateActivated = habit.getDate("dateActivated")!!
            val doneForDay = habit.get("doneForDay") as Boolean

            //calculate days habit has been active for displaying in recyclerview
            val currentDate = Date()
            val diffInMillies = abs(currentDate.time - dateActivated.time)
            val diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS)
            //Log.i(TAG, "id: ${id}, name: ${name}, category: ${category}, color: ${color}, dateActivated: ${dateActivated}, time between date started and now: ${diff}")

            val currentHabit = Habit(
                id,
                name,
                category,
                color,
                dateActivated,
                diff.toInt(),
                doneForDay
            )
            //check if category is already in list
            var categoryExists = false
            for(categoryGroup in allHabits){
                if(categoryGroup.categoryLabel.equals(category,true)){ //if category is found in habitCategoryList
                    categoryExists = true
                    //Log.i(TAG, "${currentHabit.category} found in habitCategoryList")
                    categoryGroup.habitList.add(currentHabit)
                }
            }
            //if category still hasn't been found
            if(!categoryExists){
                val newHabitList = mutableListOf(currentHabit)
                val newHabitCategory = currentHabit.category.capitalize()
                allHabits.add(HabitCategory(newHabitCategory, newHabitList))   //add habit category to category list with current habit
                //Log.i(TAG, "New category section added with category: ${currentHabit.category} ")
            }
        }

        //update habit list
        habitListOrderedByCategory.value = allHabits
    }

    private fun checkIfNewDay(){
        val dateDoc = db.collection("dates").document("dateLastUpdated")
        dateDoc.get()
            .addOnSuccessListener {
                val dateLastUpdated = it.getDate("date")!!
                val df = DateFormat.getDateInstance(DateFormat.DATE_FIELD)
                val lastDate = df.format(dateLastUpdated)
                val currentDate = df.format(Date())
                Log.i(TAG, "last date: ${lastDate}")
                Log.i(TAG, "current date: ${currentDate}")
                if(lastDate == currentDate){
                    Log.i(TAG, "It's not a new day!")
                } else {
                    Log.i(TAG, "It is a new day!")

                    //New day, so check all streaks and reset doneForDay locally
                    val newHabitList = habitListOrderedByCategory.value!!
                    for(category in newHabitList){
                        for(habit in category.habitList){
                            if(!habit.doneForDay){ //if habit wasn't done yesterday
                                habit.dateActivated = Date() //set new activation date to today
                                habit.numberOfDaysActive = 0
                            }
                            //for all habits, set doneForDay to false since it is a new day
                            habit.doneForDay = false
                        }
                    }
                    //update database
                    for(category in newHabitList){
                        for(habit in category.habitList){
                            updateExistingHabit(habit)
                        }
                    }
                }
            }
            .addOnFailureListener {exception ->
                Log.i(TAG, "Date get failed with exception: ", exception)
            }
    }

    fun addHabit(habit: Habit){
        //fill in habit data
        val habitToAdd = hashMapOf(
            "name" to habit.name.capitalize(),
            "category" to habit.category.capitalize(),
            "color" to habit.color,
            "dateActivated" to habit.dateActivated,
            "doneForDay" to habit.doneForDay
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

    fun cancelStreakUpdate(id: String){
        db.collection("habits").document(id).update("doneForDay", false)
    }

    fun updateStreak(id: String){
        db.collection("habits").document(id).update("doneForDay", true)
    }

    fun updateLastActiveDate(){
        db.collection("dates").document("dateLastUpdated").update("date", Date())
        Log.i(TAG,"Last active date: ${Date()}")
    }

    fun refreshData(){
        checkIfNewDay()
    }

    fun updateExistingHabit(updatedHabit: Habit){
        db.collection("habits").document(updatedHabit.id).update(
            "name", updatedHabit.name,
            "category", updatedHabit.category,
            "color", updatedHabit.color,
            "dateActivated", updatedHabit.dateActivated,
            "doneForDay", updatedHabit.doneForDay
        )
    }
}