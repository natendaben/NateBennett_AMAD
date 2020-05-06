package com.example.flame.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.example.flame.R
import com.example.flame.TAG
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : Fragment() {

    private lateinit var signOutButton: Button
    private lateinit var nameTextView: TextView

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = Navigation.findNavController(requireActivity(), R.id.fragment)

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        signOutButton = root.findViewById(R.id.signOutButton)
        nameTextView = root.findViewById(R.id.usernameTextView)

        signOutButton.setOnClickListener{
//            AuthUI.getInstance()
//                .signOut(requireContext())
//                .addOnCompleteListener{
//                    Log.i(TAG, "Signed out user")
//                    navController.
//                }
        }

        return root
    }

}
