package com.innoprog.android.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.innoprog.android.databinding.ActivityRootBinding
import com.innoprog.android.R

class RootActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRootBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.feedFragment -> binding.bottomNavigationView.isVisible = true
                R.id.projectsFragment -> binding.bottomNavigationView.isVisible = true
                R.id.trainingFragment -> binding.bottomNavigationView.isVisible = true
                R.id.profileFragment -> binding.bottomNavigationView.isVisible = true
                else -> binding.bottomNavigationView.isVisible = false
            }
        }
    }
}
