package com.example.lab8.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab8.R
import com.example.lab8.data.Movie

class MovieRecyclerAdapter(var movieList: List<Movie>, val itemListener: MovieItemListener): RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder>() {
    //custom view holder
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val titleText = itemView.findViewById<TextView>(R.id.titleTextView)
        val ratingText = itemView.findViewById<TextView>(R.id.ratingTextView)
    }

    //inflate
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.movie_list_item, parent, false)
        return ViewHolder(view)
    }

    //count for recyclerView
    override fun getItemCount() = movieList.count()

    //fill data and attach onClickListener
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMovie = movieList[position]

        holder.titleText.text = currentMovie.title
        holder.ratingText.text = "${currentMovie.rating} stars"

        holder.itemView.setOnClickListener {
            itemListener.onMovieItemClick(currentMovie)
        }
    }

    //interface to be implemented in mainFragment
    interface MovieItemListener{
        fun onMovieItemClick(movie: Movie)
    }
}