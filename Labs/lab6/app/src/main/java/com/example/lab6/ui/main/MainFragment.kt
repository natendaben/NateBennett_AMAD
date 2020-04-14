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
import androidx.recyclerview.widget.RecyclerView
import com.example.lab6.R

// Fragments are sections of an app's UI. Like React Components they can be rendered and reused as much as you want
// Useful for layout because larger screens like tablets might be able to display more fragments, which smaller
//      screens might need to spread fragments out across different pages of the app or something else
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val root = inflater.inflate(R.layout.main_fragment, container, false)
        //link up our viewModel to our class
        viewModel  = ViewModelProvider(this).get(MainViewModel::class.java)
        recyclerView = root.findViewById(R.id.recyclerView)


        Log.i("climbing", "hello")
        viewModel.routeData.observe(viewLifecycleOwner, Observer {
            val adapter = MainRecyclerAdapter(requireContext(), it.routes)
            Log.i("climbing", "${it.routes.count()} routes in list")
            for(route in it.routes){
                Log.i("climbing", "${route.name} is a ${route.type} climb")
            }
            recyclerView.adapter = adapter
        })
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
