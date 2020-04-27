package com.example.lab7.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(book: Book)

    @Query("SELECT * FROM books_table")
    fun getAllBooks(): LiveData<List<Book>>

    @Query("SELECT * FROM books_table WHERE book_id = :id")
    fun getBook(id: Int): Book
}