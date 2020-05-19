package com.natebennett.flame.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.natebennett.flame.data.Habit
import com.natebennett.flame.data.HabitsRepo
import com.google.firebase.auth.FirebaseUser

class MainViewModel : ViewModel() {
    private val habitsRepo = HabitsRepo()
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

    fun updateLastActiveDate(){
        habitsRepo.updateLastActiveDate()
    }

    fun refreshData(){
        habitsRepo.refreshData()
    }

    fun updateStreakInfoFromUI(id: String, name: String, category: String, color: String){
        habitsRepo.updateStreakInfoFromUI(id, name, category, color)
    }

    fun userIsLoggedIn(user: FirebaseUser){
        habitsRepo.userIsLoggedIn(user)
    }

    fun getUser(): FirebaseUser{
        return habitsRepo.getUser()
    }
}
