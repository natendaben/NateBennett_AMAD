package com.example.lab8.ui.main

import android.os.Bundle
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
import com.example.lab8.R
import com.example.lab8.data.Movie

class MovieDetailsFragment : Fragment() {

    //variables
    private lateinit var navController: NavController
    private lateinit var viewModel: MainViewModel

    private lateinit var titleTextView: TextView
    private lateinit var genreTextView: TextView
    private lateinit var ratingTextView: TextView
    private lateinit var currentMovie: Movie

    //add observer for updating view when "current movie" changes
    private val updateViewWithDetails = Observer<Movie>{
        (activity as AppCompatActivity?)?.supportActionBar?.title = "Movie Review for ${it.title}"

        currentMovie = it

        titleTextView.text = it.title
        genreTextView.text = it.genre
        ratingTextView.text = "${it.rating}/5 stars"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        navController = Navigation.findNavController(requireActivity(), R.id.fragment)

        val root = inflater.inflate(R.layout.fragment_movie_details, container, false)

        titleTextView = root.findViewById(R.id.detailTitleTextView)
        genreTextView = root.findViewById(R.id.detailGenreTextView)
        ratingTextView = root.findViewById(R.id.detailRatingTextView)

        //observe current movie selection
        viewModel.currentMovie.observe(viewLifecycleOwner, updateViewWithDetails)

        // Inflate the layout for this fragment
        return root
    }

    //attach menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //attach delete method
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.deleteMovie){
            confirmDelete()
        }
        return super.onOptionsItemSelected(item)
    }

    //show dialog to confirm deletion, then delete movie and move back to main fragment
    private fun confirmDelete(){
        val dialogBuilder = AlertDialog.Builder(requireContext())

        dialogBuilder.setMessage("Delete this movie review? This action cannot be undone.")
            .setCancelable(false)
            .setPositiveButton("Yes"){ dialog, _ ->
                viewModel.deleteMovie(currentMovie.id)
                dialog.dismiss()
                navController.navigateUp()
                val text = "Movie review deleted successfully!"
                val toast = Toast.makeText(requireContext(), text, Toast.LENGTH_LONG)
                toast.show()
            }
            .setNegativeButton("No"){ dialog, _ -> dialog.cancel()}

        val alert = dialogBuilder.create()
        alert.setTitle("Delete Review")
        alert.show()
    }
}
