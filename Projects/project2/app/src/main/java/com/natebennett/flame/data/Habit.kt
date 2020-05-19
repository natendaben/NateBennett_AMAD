package com.natebennett.flame.data

import java.util.*

data class Habit (
    val id: String,
    var name: String,
    var category: String,
    var color: String,
    var dateActivated: Date,
    var numberOfDaysActive: Int,
    var doneForDay: Boolean
)