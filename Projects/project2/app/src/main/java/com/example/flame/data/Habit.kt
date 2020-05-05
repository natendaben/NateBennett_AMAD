package com.example.flame.data

import java.time.LocalDate
import java.util.*

data class Habit (
    val id: String,
    val name: String,
    val category: String,
    val color: String,
    val dateActivated: Date,
    val numberOfDaysActive: Int,
    val doneForDay: Boolean
)