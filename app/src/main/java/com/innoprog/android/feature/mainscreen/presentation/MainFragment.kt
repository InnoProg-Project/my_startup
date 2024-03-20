package com.innoprog.android.feature.mainscreen.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentMainBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.mainscreen.di.DaggerMainScreenComponent

class MainFragment : BaseFragment<FragmentMainBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<MainScreenViewModel>()
    override fun diComponent(): ScreenComponent = DaggerMainScreenComponent.builder().build()
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
