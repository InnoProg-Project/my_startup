package com.innoprog.android.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.innoprog.android.R
import com.innoprog.android.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRootBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.rootFragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, dest, _ ->
            when (dest.id) {
                R.id.feedFragment, R.id.profileFragment, R.id.trainingListFragment, R.id.projectsFragment -> {
                    if (dest.id != R.id.profileFragment) {
                        window.statusBarColor = ContextCompat.getColor(
                            this,
                            com.innoprog.android.uikit.R.color.background_secondary
                        )
                    } else {
                        window.statusBarColor = ContextCompat.getColor(
                            this,
                            com.innoprog.android.uikit.R.color.background_primary
                        )
                    }
                    binding.bottomNavigationView.isVisible = true
                }

                else -> {
                    window.statusBarColor = ContextCompat.getColor(
                        this,
                        com.innoprog.android.uikit.R.color.background_primary
                    )
                    binding.bottomNavigationView.isVisible = false
                }
            }
        }
    }
}
