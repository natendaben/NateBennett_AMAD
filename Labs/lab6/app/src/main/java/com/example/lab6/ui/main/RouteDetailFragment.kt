package com.example.lab6.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lab6.R

/**
 * A simple [Fragment] subclass.
 */
class RouteDetailFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity?)?.supportActionBar?.title = "Route Details"

        val root = inflater.inflate(R.layout.fragment_route_detail, container, false)
        val routeName = root.findViewById<TextView>(R.id.routeName)
        val routeGrade = root.findViewById<TextView>(R.id.routeGrade)
        val routeType = root.findViewById<TextView>(R.id.routeType)
        val routePitches = root.findViewById<TextView>(R.id.routePitches)
        val routeRating = root.findViewById<TextView>(R.id.routeRating)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        sharedViewModel.currentRoute.observe(viewLifecycleOwner, Observer {
            Log.i("climbing", "Selected Route: ${it.name}")
            routeName.text = it.name
            routeGrade.text = it.rating
            if(it.pitches == 1){
                routePitches.text = "1 pitch"
            } else {
                routePitches.text = "${it.pitches.toString()} pitches"
            }
            routeRating.text = "${it.stars.toString()} stars"
            routeType.text = "${it.type} climb"
        })

        // Inflate the layout for this fragment
        return root
    }

}
