package com.example.lab6.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.lab6.R
import com.example.lab6.data.Route

/**
 * A simple [Fragment] subclass.
 */
class RouteResultsFragment : Fragment(), MainRecyclerAdapter.RouteItemListener  {

    private lateinit var viewModel: SharedViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        (activity as AppCompatActivity?)?.supportActionBar?.title = "Routes in ${viewModel.currentArea.value}"
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        val root = inflater.inflate(R.layout.fragment_route_results, container, false)
        //link up our viewModel to our class
        viewModel  = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        recyclerView = root.findViewById(R.id.recyclerView)

        Log.i("climbing", "hello")
        viewModel.routeData.observe(viewLifecycleOwner, Observer {
            val adapter = MainRecyclerAdapter(requireContext(), it.routes, this)
//            Log.i("climbing", "${it.routes.count()} routes in list")
//            for(route in it.routes){
//                Log.i("climbing", "${route.name} is a ${route.type} climb")
//            }
            recyclerView.adapter = adapter
        })
        // Inflate the layout for this fragment
        return root
    }

    override fun onRouteItemClick(route: Route) {
        Log.i("climbing", route.toString())
        navController.navigate(R.id.action_routeResultsFragment_to_routeDetailFragment)

        viewModel.currentRoute.value = route

    }

}
