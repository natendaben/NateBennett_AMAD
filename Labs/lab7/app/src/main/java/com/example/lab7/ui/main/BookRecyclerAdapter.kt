package com.example.lab7.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab7.R
import com.example.lab7.database.Book
import kotlinx.android.synthetic.main.book_list_item.view.*

class BookRecyclerAdapter(val context: Context, var bookList: List<Book>, val itemListener: BookItemListener) : RecyclerView.Adapter<BookRecyclerAdapter.ViewHolder>() {
    //custom ViewHolder
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val titleText = itemView.findViewById<TextView>(R.id.titleTextView)
        val ratingText = itemView.findViewById<TextView>(R.id.ratingTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.book_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = bookList.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentBook = bookList[position]

        holder.titleText.text = currentBook.title
        holder.ratingText.text = "${currentBook.rating} stars"
    }

    interface BookItemListener{
        fun onBookItemClick(book: Book)
    }
}