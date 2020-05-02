package com.example.flame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private val navControllerListener = NavController.OnDestinationChangedListener { controller, destination, arguments ->
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        navController = Navigation.findNavController(this, R.id.fragment)
        bottom_nav.setupWithNavController(navController)

        NavigationUI.setupActionBarWithNavController(this, navController)
        navController.addOnDestinationChangedListener(navControllerListener)

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
}
