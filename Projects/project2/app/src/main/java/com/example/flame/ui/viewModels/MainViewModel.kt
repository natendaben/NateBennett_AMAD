package com.example.flame.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flame.data.Habit
import com.example.flame.data.HabitsRepo

class MainViewModel : ViewModel() {
    private val habitsRepo = HabitsRepo()
    val habitList = habitsRepo.habitList
    var currentHabit: MutableLiveData<Habit> = MutableLiveData()
    val habitListOrderedByCategory = habitsRepo.habitListOrderedByCategory

    //attach repo add habit method
    fun addHabit(habit: Habit){
        habitsRepo.addHabit(habit)
    }

    //attach repo delete habit method
    fun deleteHabit(id: String){
        habitsRepo.deleteHabit(id)
    }

    //attach cancel streak update method
    fun cancelStreakUpdate(id: String){
        habitsRepo.cancelStreakUpdate(id)
    }

    fun updateStreak(id: String){
        habitsRepo.updateStreak(id)
    }


}
