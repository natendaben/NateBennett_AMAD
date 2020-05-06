package com.example.flame

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

/**
 * A simple [Fragment] subclass.
 */
class SignInFragment : Fragment() {

    private lateinit var signInButton: Button
    private lateinit var navController: NavController

    private val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1){
            val response = IdpResponse.fromResultIntent(data)

            if(resultCode == Activity.RESULT_OK){
                val user = FirebaseAuth.getInstance().currentUser
                Toast.makeText(requireActivity(), "Welcome, ${user?.displayName}!", Toast.LENGTH_LONG).show()
                navController.navigate(R.id.action_signInFragment_to_streaksFragment)
            } else {
                if(response != null){
                    Log.e(TAG, response.error?.localizedMessage!!)
                    Toast.makeText(requireActivity(), "Could not complete sign-in, try again!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        navController = Navigation.findNavController(requireActivity(), R.id.fragment)

        val root = inflater.inflate(R.layout.fragment_sign_in, container, false)
        signInButton = root.findViewById(R.id.signInButton)

        signInButton.setOnClickListener{
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                1
            )
        }
        // Inflate the layout for this fragment
        return root
    }

}
