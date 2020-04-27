package com.example.lab7.database

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lab7.database.BookDAO
import com.example.lab7.database.BookDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookRepository(val app: Application) {
    private val db = BookDatabase.getDatabase(app)

    //DAO references
    private val bookDao = db.bookDAO()

    //get live data object
    val allBooks: LiveData<List<Book>> = bookDao.getAllBooks()

    fun addBook(book: Book){
        CoroutineScope(Dispatchers.IO).launch {
            bookDao.insertBook(book)
        }
    }
}