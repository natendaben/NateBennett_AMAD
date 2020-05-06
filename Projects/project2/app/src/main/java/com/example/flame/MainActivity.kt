package com.example.flame

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import android.widget.ViewSwitcher
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.flame.ui.viewModels.MainViewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var viewModel: MainViewModel
    private lateinit var signInButton: Button
    private lateinit var viewSwitcher: ViewSwitcher

    private val navControllerListener = NavController.OnDestinationChangedListener { _, destination, _ ->
        if(destination.id == R.id.streaksFragment || destination.id == R.id.profileFragment) {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        when(destination.id){
            R.id.streaksFragment -> {
                supportActionBar?.title = "Streaks"
            }
            R.id.profileFragment -> {
                supportActionBar?.title = "Profile"
            }

        }
    }

    private val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1){
            val response = IdpResponse.fromResultIntent(data)

            if(resultCode == Activity.RESULT_OK){
                val user = FirebaseAuth.getInstance().currentUser
                Toast.makeText(this, "Welcome, ${user?.displayName}!", Toast.LENGTH_LONG).show()
                viewSwitcher.showNext()
            } else {
                if(response != null){
                    Log.e(TAG, response.error?.localizedMessage!!)
                    Toast.makeText(this, "Could not complete sign-in, try again!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        navController = Navigation.findNavController(this, R.id.fragment)
        bottom_nav.setupWithNavController(navController)

        signInButton = findViewById(R.id.signInButton)
        viewSwitcher = findViewById(R.id.signInSwitcher)

        signInButton.setOnClickListener{
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                1
            )
        }

        NavigationUI.setupActionBarWithNavController(this, navController)
        navController.addOnDestinationChangedListener(navControllerListener)

    }

    override fun onPause() {
        viewModel.updateLastActiveDate()
        super.onPause()
    }

    override fun onResume() {
        viewModel.refreshData()
        super.onResume()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
}
