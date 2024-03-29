package com.innoprog.android.feature.feed.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentFeedBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.feed.di.DaggerFeedComponent
import com.innoprog.android.uikit.InnoProgChipGroupView

class FeedFragment : BaseFragment<FragmentFeedBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<FeedViewModel>()
    override fun diComponent(): ScreenComponent = DaggerFeedComponent.builder().build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFeedBinding {
        return FragmentFeedBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initChips()
    }

    private fun initChips() {
        val chipTitles = listOf(ALL_CONTENT, PROJECT, IDEAS)
        binding.cgvFilter.setChips(chipTitles)
        binding.cgvFilter.setOnChipSelectListener(object :
            InnoProgChipGroupView.OnChipSelectListener {
            override fun onChipSelected(chipIndex: Int) {
                // Если нужно обработать чип
            }
        })
    }

    companion object {
        private const val ALL_CONTENT = "Всё"
        private const val PROJECT = "Проекты"
        private const val IDEAS = "Идеи"
    }
}
