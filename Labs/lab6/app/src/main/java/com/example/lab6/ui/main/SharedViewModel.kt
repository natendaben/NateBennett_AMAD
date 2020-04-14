package com.example.lab6.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lab6.data.Route
import com.example.lab6.data.RouteRepository
import com.example.lab6.utils.FileHelper

// View model is for keeping track of all the data that will be used by the UI
// Useful because it is lifecycle-conscious and prevents us from losing data on screen rotation

class SharedViewModel(app: Application) : AndroidViewModel(app) {

    //reference to repository class
    private val routeRepo = RouteRepository(app)

    //reference to live data object
    val routeData = routeRepo.routeData

    val currentRoute = MutableLiveData<Route>()
}
