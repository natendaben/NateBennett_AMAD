package com.example.lab6.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        sharedViewModel.currentRoute.observe(viewLifecycleOwner, Observer {
            Log.i("climbing", "Selected Route: ${it.name}")
        })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_route_detail, container, false)
    }

}
