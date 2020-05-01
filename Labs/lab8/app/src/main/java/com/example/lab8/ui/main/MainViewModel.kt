package com.example.lab8.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.lab8.data.Movie
import com.example.lab8.data.MovieRepository

class MainViewModel : ViewModel() {
    //references to repo, list of movies
    private val movieRepo = MovieRepository()
    val movieList = movieRepo.movieList//MutableLiveData<List<Movie>> = MutableLiveData()
    var currentMovie: MutableLiveData<Movie> = MutableLiveData()

    //attach repo add movie method
    fun addMovie(movie: Movie){
        movieRepo.addMovie(movie)
    }

    //attach repo delete movie method
    fun deleteMovie(id: String){
        movieRepo.deleteMovie(id)
    }
}
