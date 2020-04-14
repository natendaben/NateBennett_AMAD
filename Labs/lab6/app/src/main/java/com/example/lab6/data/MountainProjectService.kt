package com.example.lab6.data

import com.example.lab6.API_KEY

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MountainProjectService {
    @GET("/data/get-routes-for-lat-lon?maxDistance=2&minDiff=5.7&maxDiff=5.11c&maxResults=20&key=${API_KEY}")
    fun getRoutes(@Query("lat") lat: String, @Query("lon") long: String): Call<RouteList>
}