package com.example.flame.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.flame.R
import com.example.flame.data.Habit
import com.example.flame.ui.viewModels.MainViewModel
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class AddStreakFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var navController: NavController
    private lateinit var saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        navController = Navigation.findNavController(requireActivity(), R.id.fragment)

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_add_streak, container, false)
        saveButton = root.findViewById(R.id.saveNewStreakButton)
        saveButton.setOnClickListener{ addNewHabit() }
        return root
    }

    private fun addNewHabit(){
        viewModel.addHabit(Habit("0", "wake up early", "afternoon", "red", Date(), 0, true))
    }
}
