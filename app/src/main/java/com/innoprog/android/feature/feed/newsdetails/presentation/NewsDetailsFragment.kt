package com.innoprog.android.feature.feed.newsdetails.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentNewsDetailsBinding
import com.innoprog.android.di.AppComponentHolder
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.feed.newsdetails.di.DaggerNewsDetailsComponent
import com.innoprog.android.feature.imagegalleryadapter.ImageGalleryAdapter

class NewsDetailsFragment : BaseFragment<FragmentNewsDetailsBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<NewsDetailsViewModel>()
    private var adapter: ImageGalleryAdapter? = null

    override fun diComponent(): ScreenComponent {
        val appComponent = AppComponentHolder.getComponent()
        return DaggerNewsDetailsComponent.builder()
            .appComponent(appComponent)
            .build()
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNewsDetailsBinding {
        return FragmentNewsDetailsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUiListeners()
        initImageGallery()
    }

    private fun setUiListeners() {
        binding.newsTopBar.setLeftIconClickListener {
            viewModel.navigateUp()
        }

        binding.newsTopBar.setRightIconClickListener {
            Toast.makeText(requireContext(), "Добавлено/удалено из избранного", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initImageGallery(){
        val images = listOf(
            R.drawable.news_sample,
            R.drawable.course_logo_sample
        )

        adapter = ImageGalleryAdapter(images)
        binding.viewPager.adapter = adapter
    }
}
