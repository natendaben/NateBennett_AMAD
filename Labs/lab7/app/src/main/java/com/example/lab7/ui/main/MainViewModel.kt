package com.example.lab7.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.lab7.database.Book
import com.example.lab7.database.BookRepository

class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val bookRepo = BookRepository(app)
    val bookList: MutableLiveData<List<Book>> = MutableLiveData()
    var currentBook: MutableLiveData<Book> = MutableLiveData()

    private val bookListObserver = Observer<List<Book>> {
        val allBooks = mutableListOf<Book>()

        for(book in it){
            allBooks.add(book)
        }

        bookList.value = allBooks
    }

    init {
        bookRepo.allBooks.observeForever(bookListObserver)
    }

    override fun onCleared() {
        bookRepo.allBooks.removeObserver(bookListObserver)
        super.onCleared()
    }

    fun addBook(book: Book){
        Log.i("books_logging", "Adding book: ${book.title}")
        bookRepo.addBook(book)
    }

    fun deleteBook(book: Book){
        bookRepo.deleteBook(book)
    }

}
