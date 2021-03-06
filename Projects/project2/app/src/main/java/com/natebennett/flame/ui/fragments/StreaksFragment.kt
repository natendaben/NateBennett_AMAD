package com.natebennett.flame.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.natebennett.flame.R
import com.natebennett.flame.TAG
import com.natebennett.flame.data.Habit
import com.natebennett.flame.ui.adapters.HabitRecyclerAdapter
import com.natebennett.flame.ui.adapters.HabitSectionRecyclerAdapter
import com.natebennett.flame.ui.viewModels.MainViewModel
import com.google.firebase.auth.FirebaseAuth

class StreaksFragment : Fragment(), HabitRecyclerAdapter.HabitItemListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var navController: NavController
    private lateinit var habitRecyclerView: RecyclerView
    private lateinit var adapter: HabitSectionRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        navController = Navigation.findNavController(requireActivity(), R.id.fragment)

        setHasOptionsMenu(true)

        val root = inflater.inflate(R.layout.streaks_fragment, container, false)

        habitRecyclerView = root.findViewById(R.id.habitsRecyclerView)
        adapter = HabitSectionRecyclerAdapter(requireActivity(), emptyList(), this)
        habitRecyclerView.adapter = adapter

        //observer to update adapter when data set is changed
        viewModel.habitListOrderedByCategory.observe(viewLifecycleOwner, Observer {
            adapter.habitCategoryList = it
            adapter.notifyDataSetChanged()
        })
        return root
    }

    override fun onResume() {
        super.onResume()
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        if(firebaseUser != null) {
            Log.i(
                TAG,
                "From StreaksFragment...Current user name is ${firebaseUser?.displayName} and user id is ${firebaseUser?.uid}"
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_streak, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.addStreakButton){
            navController.navigate(R.id.action_streaksFragment_to_addStreakFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onHabitItemClick(habit: Habit) {
        //Log.i(TAG, "${habit.name} clicked")
        viewModel.currentHabit.value = habit
        navController.navigate(R.id.action_streaksFragment_to_streakDetailFragment)
    }
}
