package com.example.flame.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.flame.R
import com.example.flame.data.Habit
import com.example.flame.ui.viewModels.MainViewModel
import kotlinx.android.synthetic.main.fragment_streak_detail.*
import java.text.DateFormat

class StreakDetailFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var viewModel: MainViewModel

    private lateinit var currentHabit: Habit
    private lateinit var streakResetButton: Button
    private lateinit var streakIconContainer: ConstraintLayout

    //add observer for updating view when "current habit" changes
    private val updateViewWithDetails = Observer<Habit>{
        currentHabit = it

        (activity as AppCompatActivity?)?.supportActionBar?.title = currentHabit.name

        //Set text views
        val daysActive: Int
        if(currentHabit.doneForDay){
            daysActive = currentHabit.numberOfDaysActive + 1
        } else {
            daysActive = currentHabit.numberOfDaysActive
        }
        if(daysActive == 1){
            streakLengthTextView.text = "1 day!"
        } else {
            streakLengthTextView.text = "${daysActive} days!"
        }
        val df = DateFormat.getDateInstance(DateFormat.LONG)
        streakSinceTextView.text = df.format(currentHabit.dateActivated)
        streakCategoryTextView.text = currentHabit.category

        //Initialize icon
        if(currentHabit.doneForDay){
            streakIcon.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.colorf, null))
            streakIconContainer.background = ResourcesCompat.getDrawable(resources, R.drawable.colorbg, null)
        } else {
            streakIcon.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.greyf, null))
            streakIconContainer.background = ResourcesCompat.getDrawable(resources, R.drawable.greybg, null)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        navController = Navigation.findNavController(requireActivity(),
            R.id.fragment
        )
        //observe current movie selection
        viewModel.currentHabit.observe(viewLifecycleOwner, updateViewWithDetails)

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_streak_detail, container, false)
        streakResetButton = root.findViewById(R.id.resetStreakButton)
        streakIconContainer = root.findViewById(R.id.streakIconContainer)

        //hook up buttons
        streakResetButton.setOnClickListener{ cancelStreakUpdate() }
        streakIconContainer.setOnClickListener { updateStreak() }

        return root
    }

    private fun cancelStreakUpdate(){
        viewModel.cancelStreakUpdate(currentHabit.id)
        streakIcon.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.greyf, null))
        streakIconContainer.background = ResourcesCompat.getDrawable(resources, R.drawable.greybg, null)
        val daysActive = currentHabit.numberOfDaysActive
        if(daysActive == 1){
            streakLengthTextView.text = "1 day!"
        } else {
            streakLengthTextView.text = "${daysActive} days!"
        }
    }

    private fun updateStreak(){
        viewModel.updateStreak(currentHabit.id)
        streakIcon.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.colorf, null))
        streakIconContainer.background = ResourcesCompat.getDrawable(resources, R.drawable.colorbg, null)
        val daysActive = currentHabit.numberOfDaysActive + 1
        if(daysActive == 1){
            streakLengthTextView.text = "1 day!"
        } else {
            streakLengthTextView.text = "${daysActive} days!"
        }
    }

}
