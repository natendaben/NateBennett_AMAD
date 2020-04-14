package com.example.lab6.data

import android.app.Application
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.example.lab6.BASE_URL
import com.example.lab6.utils.FileHelper
import com.example.lab6.utils.NetworkHelper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RouteRepository(val app: Application) {

    val routeData = MutableLiveData<RouteList>()

    //Retrofig config
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private var service: MountainProjectService

    //fetch the data when class is instantiated
    init {
        service = retrofit.create(MountainProjectService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            getRouteData("40.00", "-105.41")
        }
    }

    @WorkerThread
    private suspend fun getRouteData(lat: String, long: String){
        if(NetworkHelper.networkConnected(app)){
            //valid network connection
            val response = service.getRoutes(lat, long).execute()
            if(response.body() != null){
                val responseBody = response.body()
                routeData.postValue(responseBody)
            } else {
                Log.e("climbing", "Could not search for routes. Error code: ${response.code()}")
            }
        } else {
            Log.e("climbing", "No network connection")
        }
//        val rawText = FileHelper.readText(app, "sample.json")
////        Log.i("climbing", rawText)
//
//        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
//        //tell adapter what format the data will be in (see listType variable declaration)
//        val adapter: JsonAdapter<RouteList> = moshi.adapter(RouteList::class.java)
//
//        //update live data object
//        routeData.value = adapter.fromJson(rawText) ?: RouteList(routes = emptyList()) //elvis operator makes an empty list if something goes wrong
    }
}