package com.example.lab6.data

data class Route (
    //names here must match raw data fields in our json
    val name: String,
    val type: String,
    val rating: String,
    val stars: Double,
    val pitches: Int
)

data class RouteList (
    val routes: List<Route>
)