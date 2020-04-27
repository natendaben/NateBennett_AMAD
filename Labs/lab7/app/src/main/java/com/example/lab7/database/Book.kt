package com.example.lab7.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books_table")
data class Book (
    @PrimaryKey(autoGenerate = true) val book_id: Int = 0,
    val title: String,
    val rating: Int,
    val pages: Int
)