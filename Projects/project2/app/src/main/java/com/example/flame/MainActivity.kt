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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottom_nav: BottomNavigationView
    private lateinit var viewModel: MainViewModel

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
        if(destination.id == R.id.signInFragment){
            bottom_nav.visibility = android.view.View.GONE
            supportActionBar?.hide()
        } else {
            bottom_nav.visibility = android.view.View.VISIBLE
            supportActionBar?.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        navController = Navigation.findNavController(this, R.id.fragment)
        bottom_nav = findViewById(R.id.bottom_nav)
        bottom_nav.setupWithNavController(navController)

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

    override fun onDestroy() {
        super.onDestroy()
        FirebaseAuth.getInstance().signOut()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
}
