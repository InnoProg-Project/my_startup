package com.innoprog.android.feature.feed.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentFeedBinding
import com.innoprog.android.base.ViewModelSample

class FeedFragment : BaseFragment<FragmentFeedBinding>() {
    override val viewModel: BaseViewModel by viewModels<ViewModelSample>()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFeedBinding {
        return FragmentFeedBinding.inflate(inflater, container, false)
    }
}
