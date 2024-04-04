package com.innoprog.android.feature.mainscreen.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentMainBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.feed.presentation.FeedFragment
import com.innoprog.android.feature.mainscreen.di.DaggerMainScreenComponent
import com.innoprog.android.feature.profile.profiledetails.presentation.ProfileFragment
import com.innoprog.android.feature.projects.presentation.ProjectsFragment
import com.innoprog.android.feature.training.trainingList.presentation.TrainingListFragment

class MainFragment : BaseFragment<FragmentMainBinding, BaseViewModel>() {

    private var lastSelectedItem: Int? = null

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

        childFragmentManager.commit {
            replace(
                R.id.mainFragmentContainerView,
                getPageFragment(lastSelectedItem ?: R.id.feedFragment)
            )
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            childFragmentManager.commit {
                replace(R.id.mainFragmentContainerView, getPageFragment(it.itemId))
            }
            lastSelectedItem = it.itemId
            true
        }
    }

    private fun getPageFragment(selectedItemId: Int): Fragment {
        return when (selectedItemId) {
            R.id.feedFragment -> FeedFragment()
            R.id.projectsFragment -> ProjectsFragment()
            R.id.trainingFragment -> TrainingListFragment()
            R.id.profileFragment -> ProfileFragment()
            else -> error("")
        }
    }
}
