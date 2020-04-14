package com.example.lab6.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Route (
    //names here must match raw data fields in our json
    val name: String,
    val type: String,
    val rating: String,
    val stars: Double,
    val pitches: Int
)

@JsonClass(generateAdapter = true)
data class RouteList (
    val routes: List<Route>
)