package com.example.flame.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.example.flame.R
import com.example.flame.TAG
import com.example.flame.ui.viewModels.MainViewModel
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : Fragment() {

    private lateinit var signOutButton: Button
    private lateinit var nameTextView: TextView
    private lateinit var viewModel: MainViewModel

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = Navigation.findNavController(requireActivity(), R.id.fragment)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        signOutButton = root.findViewById(R.id.signOutButton)
        nameTextView = root.findViewById(R.id.usernameTextView)

        signOutButton.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            AuthUI.getInstance().signOut(requireContext())
                .addOnCompleteListener{
                    Log.i(TAG, "Signed out user")
                    navController.navigate(R.id.action_profileFragment_to_signInFragment)
                    Toast.makeText(requireActivity(), "Signed out successfully", Toast.LENGTH_SHORT).show()

                }
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        var user = FirebaseAuth.getInstance().currentUser
        if(user != null){
            nameTextView.text = user.displayName
        } else {
            user = viewModel.getUser()
            nameTextView.text = user.displayName
        }
        //nameTextView.text = FirebaseAuth.getInstance().currentUser?.displayName ?: "Welcome to your profile!"

    }

}
