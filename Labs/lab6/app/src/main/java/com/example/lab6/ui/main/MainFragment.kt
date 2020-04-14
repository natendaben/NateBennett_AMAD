package com.example.lab6.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
class MainFragment : Fragment(), MainRecyclerAdapter.RouteItemListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: SharedViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        val root = inflater.inflate(R.layout.main_fragment, container, false)
        //link up our viewModel to our class
        viewModel  = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        recyclerView = root.findViewById(R.id.recyclerView)


        Log.i("climbing", "hello")
        viewModel.routeData.observe(viewLifecycleOwner, Observer {
            val adapter = MainRecyclerAdapter(requireContext(), it.routes, this)
            Log.i("climbing", "${it.routes.count()} routes in list")
            for(route in it.routes){
                Log.i("climbing", "${route.name} is a ${route.type} climb")
            }
            recyclerView.adapter = adapter
        })
        return root
    }

    override fun onRouteItemClick(route: Route) {
        Log.i("climbing", route.toString())
        navController.navigate(R.id.action_mainFragment_to_routeDetailFragment)

        viewModel.currentRoute.value = route

    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}
