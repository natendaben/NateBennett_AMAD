package com.example.flame.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.flame.R
import com.example.flame.data.Habit
import com.example.flame.ui.viewModels.MainViewModel

/**
 * A simple [Fragment] subclass.
 */
class StreakDetailFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var viewModel: MainViewModel

    private lateinit var currentHabit: Habit

    //private lateinit var textView: TextView

    //add observer for updating view when "current habit" changes
    private val updateViewWithDetails = Observer<Habit>{
        currentHabit = it

        (activity as AppCompatActivity?)?.supportActionBar?.title = currentHabit.name
        //textView.text = it.name
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        navController = Navigation.findNavController(requireActivity(),
            R.id.fragment
        )

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_streak_detail, container, false)
        //textView = root.findViewById(R.id.testTextView)

        //observe current movie selection
        viewModel.currentHabit.observe(viewLifecycleOwner, updateViewWithDetails)

        return root
    }

}
