package com.natebennett.flame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.natebennett.flame.ui.viewModels.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNav: BottomNavigationView
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
            bottomNav.visibility = android.view.View.GONE
            supportActionBar?.hide()
        } else {
            bottomNav.visibility = android.view.View.VISIBLE
            supportActionBar?.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        navController = Navigation.findNavController(this, R.id.fragment)
        bottomNav = findViewById(R.id.bottom_nav)
        bottomNav.setupWithNavController(navController)

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
