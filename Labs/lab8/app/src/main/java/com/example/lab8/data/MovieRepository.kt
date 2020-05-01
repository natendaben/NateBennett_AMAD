package com.example.lab8.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MovieRepository {
    //reference to database
    private val db = Firebase.firestore

    //movie list, updated by snapshot listener
    val movieList = MutableLiveData<List<Movie>>()

    init{
        //add snapshot listener to database for when movies are added or removed
        db.collection("movies").addSnapshotListener{snapshot, error ->
            if(error != null){
                //handle error
                Log.i("movies", "Firebase listen failed", error)
                return@addSnapshotListener
            }
            if(snapshot != null){
                parseAllMovies(snapshot)
            } else {
                Log.w("movies", "Data is null")
            }
        }
    }

    //function for refreshing movie data whenever database changes
    private fun parseAllMovies(result: QuerySnapshot){
        var allMovies = mutableListOf<Movie>()

        for(movie in result){
            val id: String = movie.id //id to be used for deleting movie later if desired
            val title = movie.getString("title")!!
            val genre = movie.getString("genre")!!
            val rating = (movie.get("rating") as Long).toInt()

            allMovies.add(Movie(
                id,
                title,
                genre,
                rating
            ))
        }
//        for(movie in allMovies){
//            Log.i("movies", movie.title)
//        }

        //update movie list
        movieList.value = allMovies
    }

    //function to add movie
    fun addMovie(movie: Movie){
        //fill in movie data
        val movieToAdd = hashMapOf(
            "title" to movie.title,
            "genre" to movie.genre,
            "rating" to movie.rating
        )
        //add movie to database
        db.collection("movies")
            .add(movieToAdd)
            .addOnSuccessListener {
                Log.i("movies", "DocumentSnapshot added with ID: ${it.id}")
            }
            .addOnFailureListener { e ->
                Log.w("movies", "Error adding document", e)
            }
    }

    //function to delete movie
    fun deleteMovie(id: String){
        db.collection("movies").document(id).delete()
    }
}