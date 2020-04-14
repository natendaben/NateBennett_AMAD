package com.example.lab6.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.lab6.R
import com.example.lab6.data.Route

// Fragments are sections of an app's UI. Like React Components they can be rendered and reused as much as you want
// Useful for layout because larger screens like tablets might be able to display more fragments, which smaller
//      screens might need to spread fragments out across different pages of the app or something else
class MainFragment : Fragment(){

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var navController: NavController
    private lateinit var areaSpinner: Spinner
    private lateinit var button: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        (activity as AppCompatActivity?)?.supportActionBar?.title = "Colorado Climbing"

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        val root = inflater.inflate(R.layout.main_fragment, container, false)

        areaSpinner = root.findViewById(R.id.climbingAreaSpinner)
        button = root.findViewById(R.id.button)

        button.setOnClickListener{findRoutes()}

        return root
    }

    private fun findRoutes(){
        var climbingArea = areaSpinner.selectedItem
        //Log.i("climbing", "${climbingArea} has been selected")
        sharedViewModel.currentArea.value = climbingArea.toString()
        navController.navigate(R.id.action_mainFragment_to_routeResultsFragment)
    }


}
