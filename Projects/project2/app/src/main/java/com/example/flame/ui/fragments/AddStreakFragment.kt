package com.example.flame.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.flame.R
import com.example.flame.data.Habit
import com.example.flame.ui.viewModels.MainViewModel
import androidx.lifecycle.Observer
import com.example.flame.TAG
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class AddStreakFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var navController: NavController

    private lateinit var nameEditText: EditText
    private lateinit var categoryEditText: EditText
    private lateinit var saveButton: Button

    private lateinit var yellowSelector: ConstraintLayout
    private lateinit var orangeSelector: ConstraintLayout
    private lateinit var redSelector: ConstraintLayout
    private lateinit var pinkSelector: ConstraintLayout
    private lateinit var purpleSelector: ConstraintLayout
    private lateinit var darkblueSelector: ConstraintLayout
    private lateinit var blueSelector: ConstraintLayout
    private lateinit var lightblueSelector: ConstraintLayout
    private lateinit var lightgreenSelector: ConstraintLayout
    private lateinit var greenSelector: ConstraintLayout

    private var selectedColor: MutableLiveData<String> = MutableLiveData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity?)?.supportActionBar?.title = "Add New Habit"
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        navController = Navigation.findNavController(requireActivity(), R.id.fragment)

        selectedColor.value = "yellow"

        selectedColor.observe(viewLifecycleOwner, updateColorSelectors)

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_add_streak, container, false)
        nameEditText = root.findViewById(R.id.nameEditText)
        categoryEditText = root.findViewById(R.id.categoryEditText)
        saveButton = root.findViewById(R.id.saveButton)

        saveButton.setOnClickListener{ addNewHabit() }

        yellowSelector = root.findViewById(R.id.yellowColorContainer)
        orangeSelector = root.findViewById(R.id.orangeColorContainer)
        redSelector = root.findViewById(R.id.redColorContainer)
        pinkSelector = root.findViewById(R.id.pinkColorContainer)
        purpleSelector = root.findViewById(R.id.purpleColorContainer)
        darkblueSelector = root.findViewById(R.id.darkblueColorContainer)
        blueSelector = root.findViewById(R.id.blueColorContainer)
        lightblueSelector = root.findViewById(R.id.lightblueColorContainer)
        lightgreenSelector = root.findViewById(R.id.lightgreenColorContainer)
        greenSelector = root.findViewById(R.id.greenColorContainer)

        yellowSelector.setOnClickListener { selectedColor.value = "yellow" }
        orangeSelector.setOnClickListener { selectedColor.value = "orange" }
        redSelector.setOnClickListener { selectedColor.value = "red" }
        pinkSelector.setOnClickListener { selectedColor.value = "pink" }
        purpleSelector.setOnClickListener { selectedColor.value = "purple" }
        darkblueSelector.setOnClickListener { selectedColor.value = "darkblue" }
        blueSelector.setOnClickListener { selectedColor.value = "blue" }
        lightblueSelector.setOnClickListener { selectedColor.value = "lightblue" }
        lightgreenSelector.setOnClickListener { selectedColor.value = "lightgreen" }
        greenSelector.setOnClickListener { selectedColor.value = "green" }

        return root
    }

    private fun addNewHabit(){
        if(nameEditText.text.toString() != "" && categoryEditText.text.toString() != ""){
            viewModel.addHabit(Habit(
                "0",
                nameEditText.text.toString(),
                categoryEditText.text.toString(),
                selectedColor.value.toString(),
                Date(),
                0,
                false
            ))
            navController.navigateUp()
            val text = "${nameEditText.text} added successfully!"
            Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(requireContext(), "Please make sure you've entered a name and a category!", Toast.LENGTH_LONG).show()
        }
    }

    private val updateColorSelectors = Observer<String>{
        val selectedColor = it
        colorHelperFunction(selectedColor)
    }

    private fun colorHelperFunction(selectedColor: String){
        val yellow = ResourcesCompat.getDrawable(resources, R.drawable.yellow, null)
        val orange = ResourcesCompat.getDrawable(resources, R.drawable.orange, null)
        val red = ResourcesCompat.getDrawable(resources, R.drawable.red, null)
        val pink = ResourcesCompat.getDrawable(resources, R.drawable.pink, null)
        val purple = ResourcesCompat.getDrawable(resources, R.drawable.purple, null)
        val darkblue = ResourcesCompat.getDrawable(resources, R.drawable.darkblue, null)
        val blue = ResourcesCompat.getDrawable(resources, R.drawable.blue, null)
        val lightblue = ResourcesCompat.getDrawable(resources, R.drawable.lightblue, null)
        val lightgreen = ResourcesCompat.getDrawable(resources, R.drawable.lightgreen, null)
        val green = ResourcesCompat.getDrawable(resources, R.drawable.green, null)

        val yellowS = ResourcesCompat.getDrawable(resources, R.drawable.yellow_selected, null)
        val orangeS = ResourcesCompat.getDrawable(resources, R.drawable.orange_selected, null)
        val redS = ResourcesCompat.getDrawable(resources, R.drawable.red_selected, null)
        val pinkS = ResourcesCompat.getDrawable(resources, R.drawable.pink_selected, null)
        val purpleS = ResourcesCompat.getDrawable(resources, R.drawable.purple_selected, null)
        val darkblueS = ResourcesCompat.getDrawable(resources, R.drawable.darkblue_selected, null)
        val blueS = ResourcesCompat.getDrawable(resources, R.drawable.blue_selected, null)
        val lightblueS = ResourcesCompat.getDrawable(resources, R.drawable.lightblue_selected, null)
        val lightgreenS = ResourcesCompat.getDrawable(resources, R.drawable.lightgreen_selected, null)
        val greenS = ResourcesCompat.getDrawable(resources, R.drawable.green_selected, null)


        when(selectedColor){
            "yellow" -> {
                yellowSelector.background = yellowS
                orangeSelector.background = orange
                redSelector.background = red
                pinkSelector.background = pink
                purpleSelector.background = purple
                darkblueSelector.background = darkblue
                blueSelector.background = blue
                lightblueSelector.background = lightblue
                lightgreenSelector.background = lightgreen
                greenSelector.background = green
            }
            "orange" -> {
                yellowSelector.background = yellow
                orangeSelector.background = orangeS
                redSelector.background = red
                pinkSelector.background = pink
                purpleSelector.background = purple
                darkblueSelector.background = darkblue
                blueSelector.background = blue
                lightblueSelector.background = lightblue
                lightgreenSelector.background = lightgreen
                greenSelector.background = green
            }
            "red" -> {
                yellowSelector.background = yellow
                orangeSelector.background = orange
                redSelector.background = redS
                pinkSelector.background = pink
                purpleSelector.background = purple
                darkblueSelector.background = darkblue
                blueSelector.background = blue
                lightblueSelector.background = lightblue
                lightgreenSelector.background = lightgreen
                greenSelector.background = green
            }
            "pink" -> {
                yellowSelector.background = yellow
                orangeSelector.background = orange
                redSelector.background = red
                pinkSelector.background = pinkS
                purpleSelector.background = purple
                darkblueSelector.background = darkblue
                blueSelector.background = blue
                lightblueSelector.background = lightblue
                lightgreenSelector.background = lightgreen
                greenSelector.background = green
            }
            "purple" -> {
                yellowSelector.background = yellow
                orangeSelector.background = orange
                redSelector.background = red
                pinkSelector.background = pink
                purpleSelector.background = purpleS
                darkblueSelector.background = darkblue
                blueSelector.background = blue
                lightblueSelector.background = lightblue
                lightgreenSelector.background = lightgreen
                greenSelector.background = green
            }
            "darkblue" -> {
                yellowSelector.background = yellow
                orangeSelector.background = orange
                redSelector.background = red
                pinkSelector.background = pink
                purpleSelector.background = purple
                darkblueSelector.background = darkblueS
                blueSelector.background = blue
                lightblueSelector.background = lightblue
                lightgreenSelector.background = lightgreen
                greenSelector.background = green
            }
            "blue" -> {
                yellowSelector.background = yellow
                orangeSelector.background = orange
                redSelector.background = red
                pinkSelector.background = pink
                purpleSelector.background = purple
                darkblueSelector.background = darkblue
                blueSelector.background = blueS
                lightblueSelector.background = lightblue
                lightgreenSelector.background = lightgreen
                greenSelector.background = green
            }
            "lightblue" -> {
                yellowSelector.background = yellow
                orangeSelector.background = orange
                redSelector.background = red
                pinkSelector.background = pink
                purpleSelector.background = purple
                darkblueSelector.background = darkblue
                blueSelector.background = blue
                lightblueSelector.background = lightblueS
                lightgreenSelector.background = lightgreen
                greenSelector.background = green
            }
            "lightgreen" -> {
                yellowSelector.background = yellow
                orangeSelector.background = orange
                redSelector.background = red
                pinkSelector.background = pink
                purpleSelector.background = purple
                darkblueSelector.background = darkblue
                blueSelector.background = blue
                lightblueSelector.background = lightblue
                lightgreenSelector.background = lightgreenS
                greenSelector.background = green
            }
            "green" -> {
                yellowSelector.background = yellow
                orangeSelector.background = orange
                redSelector.background = red
                pinkSelector.background = pink
                purpleSelector.background = purple
                darkblueSelector.background = darkblue
                blueSelector.background = blue
                lightblueSelector.background = lightblue
                lightgreenSelector.background = lightgreen
                greenSelector.background = greenS
            }
            else -> {
                yellowSelector.background = yellowS
                orangeSelector.background = orange
                redSelector.background = red
                pinkSelector.background = pink
                purpleSelector.background = purple
                darkblueSelector.background = darkblue
                blueSelector.background = blue
                lightblueSelector.background = lightblue
                lightgreenSelector.background = lightgreen
                greenSelector.background = green
            }
        }
    }
}
