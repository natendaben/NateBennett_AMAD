package com.example.lab6.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.lab6.utils.FileHelper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class RouteRepository(val app: Application) {

    val routeData = MutableLiveData<RouteList>()

    init {
        getRouteData()
    }
    private fun getRouteData(){
        val rawText = FileHelper.readText(app, "sample.json")
//        Log.i("climbing", rawText)

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        //tell adapter what format the data will be in (see listType variable declaration)
        val adapter: JsonAdapter<RouteList> = moshi.adapter(RouteList::class.java)

        //update live data object
        routeData.value = adapter.fromJson(rawText) ?: RouteList(routes = emptyList()) //elvis operator makes an empty list if something goes wrong
    }
}