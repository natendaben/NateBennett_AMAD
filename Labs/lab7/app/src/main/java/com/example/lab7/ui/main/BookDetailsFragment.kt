package com.example.lab7.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.lab7.R
import com.example.lab7.database.Book

/**
 * A simple [Fragment] subclass.
 */
class BookDetailsFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var viewModel: MainViewModel

    private lateinit var titleTextView: TextView
    private lateinit var pagesTextView: TextView
    private lateinit var ratingTextView: TextView
    private lateinit var currentBook: Book //try this again!!!

    private val updateViewWithDetails = Observer<Book>{
        (activity as AppCompatActivity?)?.supportActionBar?.title = "Book Review for ${it.title}"

        currentBook = it

        titleTextView.text = it.title
        pagesTextView.text = "${it.pages} pages"
        ratingTextView.text = "${it.rating}/5 stars"

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_book_details, container, false)

        titleTextView = root.findViewById(R.id.detailTitleTextView)
        pagesTextView = root.findViewById(R.id.detailPagesTextView)
        ratingTextView = root.findViewById(R.id.detailRatingTextView)

        viewModel.currentBook.observe(viewLifecycleOwner, updateViewWithDetails)

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.book_details_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.delete){
            confirmDelete()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmDelete(){
        val dialogBuilder = AlertDialog.Builder(requireContext())

        dialogBuilder.setMessage("Delete this book review? This action cannot be undone.")
            .setCancelable(false)
            .setPositiveButton("Yes"){ dialog, _ ->
                viewModel.deleteBook(currentBook)
                dialog.dismiss()
                navController.navigateUp()
                val text = "Book review deleted successfully!"
                val toast = Toast.makeText(requireContext(), text, Toast.LENGTH_LONG)
                toast.show()
            }
            .setNegativeButton("No"){ dialog, _ -> dialog.cancel()}

        val alert = dialogBuilder.create()
        alert.setTitle("Delete Review")
        alert.show()
    }
}
