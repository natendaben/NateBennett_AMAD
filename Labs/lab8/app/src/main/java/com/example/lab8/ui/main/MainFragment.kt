package com.example.lab8.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.lab8.R
import com.example.lab8.data.Movie

class MainFragment : Fragment(), MovieRecyclerAdapter.MovieItemListener  {

    //variables
    private lateinit var viewModel: MainViewModel
    private lateinit var navController: NavController
    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var adapter: MovieRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        navController = Navigation.findNavController(requireActivity(), R.id.fragment)

        (activity as AppCompatActivity?)?.supportActionBar?.title = "Movie Reviews"

        val root = inflater.inflate(R.layout.main_fragment, container, false)

        movieRecyclerView = root.findViewById(R.id.movieRecyclerView)
        adapter = MovieRecyclerAdapter(emptyList(), this)
        movieRecyclerView.adapter = adapter

        //add observer to refresh adapter when data is changed
        viewModel.movieList.observe(viewLifecycleOwner, Observer {
            adapter.movieList = it
            adapter.notifyDataSetChanged()
        })
        return root
    }

    //attach menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //set up navigation for adding movie
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.addMovie){
            navController.navigate(R.id.action_mainFragment_to_addMovieFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    //set up navigation for clicking on a movie
    override fun onMovieItemClick(movie: Movie) {
        viewModel.currentMovie.value = movie
        navController.navigate(R.id.action_mainFragment_to_movieDetailsFragment)
    }

}
