package com.innoprog.android.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.innoprog.android.R
import com.innoprog.android.databinding.FragmentMainBinding
import com.innoprog.android.di.DaggerAppComponent
import javax.inject.Inject

class MainFragment : BaseFragment<FragmentMainBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override val viewModel: BaseViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ViewModelSample::class.java]
    }

    private val component by lazy {
        DaggerAppComponent.builder().build().apply {
            inject(this@MainFragment)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
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
