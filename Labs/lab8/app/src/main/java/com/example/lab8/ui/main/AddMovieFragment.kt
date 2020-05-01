package com.example.lab8.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.lab8.R
import com.example.lab8.data.Movie
import com.example.lab8.hideKeyboard

class AddMovieFragment : Fragment() {

    //variables
    private lateinit var viewModel: MainViewModel
    private lateinit var navController: NavController
    private lateinit var titleTextInput: EditText
    private lateinit var genreTextInput: EditText
    private lateinit var ratingRadioGroup: RadioGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        (activity as AppCompatActivity?)?.supportActionBar?.title = "Review New Movie"

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        navController = Navigation.findNavController(requireActivity(), R.id.fragment)

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_add_movie, container, false)

        titleTextInput = root.findViewById(R.id.titleTextInput)
        genreTextInput = root.findViewById(R.id.genreTextInput)
        ratingRadioGroup = root.findViewById(R.id.starsRadioGroup)

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.saveMovie){
            if(titleTextInput.text != null && genreTextInput != null && ratingRadioGroup.checkedRadioButtonId != -1){
                val movieTitle = titleTextInput.text
                val movieGenre = genreTextInput.text
                val starsId = ratingRadioGroup.checkedRadioButtonId
                var stars: Int = 0

                when(starsId){
                    R.id.one -> stars = 1
                    R.id.two -> stars = 2
                    R.id.three -> stars = 3
                    R.id.four -> stars = 4
                    R.id.five -> stars = 5
                }

                Log.i("movies", "You've added ${movieTitle} which is a ${movieGenre} movie and you rated it ${stars} stars")
                viewModel.addMovie(Movie(title = movieTitle.toString(), rating = stars, genre = movieGenre.toString(), id = "0"))
                hideKeyboard()
                navController.navigateUp()
                val text = "Movie review added successfully!"
                Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
            } else {
                val text = "Please make sure you've entered all the information for your review!"
                Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
