package com.innoprog.android.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.innoprog.android.R
import com.innoprog.android.databinding.FragmentMainBinding
import com.innoprog.android.di.AppComponent
import com.innoprog.android.di.DaggerAppComponent

class MainFragment : BaseFragment<FragmentMainBinding, ViewModelSample>() {
    override fun diComponent(): AppComponent = DaggerAppComponent.builder().build()

    override val viewModel by injectViewModel<ViewModelSample>()

    override fun onCreate(savedInstanceState: Bundle?) {
        diComponent().inject(this@MainFragment)
        super.onCreate(savedInstanceState)
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController =
            (childFragmentManager.findFragmentById(R.id.mainFragmentContainerView) as NavHostFragment)
                .navController

        binding.bottomNavigationView.setupWithNavController(navController)
    }
}
