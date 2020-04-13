package com.example.lab6.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lab6.R

// Fragments are sections of an app's UI. Like React Components they can be rendered and reused as much as you want
// Useful for layout because larger screens like tablets might be able to display more fragments, which smaller
//      screens might need to spread fragments out across different pages of the app or something else
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        //link up our viewModel to our class
        viewModel  = ViewModelProvider(this).get(MainViewModel::class.java)

        Log.i("climbing", "hello")
        viewModel.routeData.observe(viewLifecycleOwner, Observer {
            Log.i("climbing", "${it.routes.count()} routes in list")
            for(route in it.routes){
                Log.i("climbing", "${route.name} is a ${route.type} climb")
            }
        })
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
