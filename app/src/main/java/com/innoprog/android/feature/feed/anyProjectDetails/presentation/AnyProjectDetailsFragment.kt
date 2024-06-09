package com.innoprog.android.feature.feed.anyProjectDetails.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.tabs.TabLayoutMediator
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentAnyProjectDetailsBinding
import com.innoprog.android.di.AppComponentHolder
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.feed.anyProjectDetails.di.DaggerAnyProjectDetailsComponent
import com.innoprog.android.feature.feed.anyProjectDetails.domain.models.AnyProjectDetailsModel
import com.innoprog.android.feature.imagegalleryadapter.ImageGalleryAdapter
import com.innoprog.android.feature.training.common.VerticalSpaceDecorator
import com.innoprog.android.feature.training.courseInformation.presentation.DocumentRecyclerViewAdapter
import com.innoprog.android.uikit.R

class AnyProjectDetailsFragment : BaseFragment<FragmentAnyProjectDetailsBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<AnyProjectDetailsViewModel>()

    private var galleryAdapter: ImageGalleryAdapter? = null

    //private var listNews = ArrayList<>()

    /*private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter(listNews) { news ->
            val action = AnyProjectFragmentDirections.actionProjectFragmentToNewsDetailsFragment(news.id)
            findNavController().navigate(action)
        }
    }*/

    private var documentAdapter: DocumentRecyclerViewAdapter? = null
    private val decorator: VerticalSpaceDecorator by lazy {
        VerticalSpaceDecorator(resources.getDimensionPixelSize(R.dimen.margin_8))
    }

    override fun diComponent(): ScreenComponent {
        val appComponent = AppComponentHolder.getComponent()
        return DaggerAnyProjectDetailsComponent.builder()
            .appComponent(appComponent)
            .build()
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAnyProjectDetailsBinding {
        return FragmentAnyProjectDetailsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUiListeners()

        viewModel.screenState.observe(viewLifecycleOwner) {
            updateUI(it)
        }

        viewModel.getAnyProjectDetails("555")

        //binding.rvPublications.adapter = newsAdapter
        documentAdapter = DocumentRecyclerViewAdapter { url ->
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
        binding.rvDocuments.addItemDecoration(decorator)
        binding.rvDocuments.adapter = documentAdapter
    }

    private fun setUiListeners() {
        binding.apply {
            topBar.setLeftIconClickListener {
                viewModel.navigateUp()
            }
        }
    }

    private fun initImageGallery() {
        val images = listOf(
            com.innoprog.android.R.drawable.news_sample,
            com.innoprog.android.R.drawable.course_logo_sample,
            com.innoprog.android.R.drawable.news_sample,
            com.innoprog.android.R.drawable.course_logo_sample,
            com.innoprog.android.R.drawable.news_sample,
        )

        galleryAdapter = ImageGalleryAdapter(images)
        binding.viewPager.adapter = galleryAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position -> }.attach()
    }

    private fun updateUI(state: AnyProjectDetailsScreenState) {
        when (state) {
            is AnyProjectDetailsScreenState.Loading -> showLoading()
            is AnyProjectDetailsScreenState.Content -> showContent(state.anyProjectDetails)
            is AnyProjectDetailsScreenState.Error -> showError()
        }
    }

    private fun showLoading() {
        Toast.makeText(requireContext(), "Загрузка", Toast.LENGTH_SHORT).show()
    }

    private fun showError() {
        Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
    }

    private fun showContent(anyProjectDetails: AnyProjectDetailsModel) {
        binding.apply {
            initImageGallery()
            loadProjectInfo(anyProjectDetails)
            tvShortDescription.text = anyProjectDetails.shortDescription
            tvDescription.text = anyProjectDetails.description
            tvFinancingStageValue.text = anyProjectDetails.financingStage
            tvDeadline.text = anyProjectDetails.deadline


            /*Glide.with(requireContext())
                .load(anyProject.logoFilePath)
                .placeholder(R.drawable.ic_placeholder_logo)
                .centerCrop()
                .transform(RoundedCorners(radius))
                .into(ivProjectLogo)

            tvProjectName.text = anyProject.name
            tvProjectDirection.text = anyProject.area
            tvProjectDescription.text = anyProject.shortDescription
            tvProjectNews.text =
                format(getString(R.string.project_news), anyProject.publicationsCount)

            if (anyProject.projectNews != null) {
                rvPublications.isVisible = true
                newsAdapter.newsList.clear()
                newsAdapter.newsList.addAll(anyProject.projectNews)
                newsAdapter.notifyDataSetChanged()
            } else {
                rvPublications.isVisible = false
            }*/
        }
    }

    private fun loadProjectInfo(anyProjectDetails: AnyProjectDetailsModel) {
        val radius = binding.root.resources.getDimensionPixelSize(com.innoprog.android.R.dimen.corner_radius_8)
        binding.apply {
            Glide
                .with(requireContext())
                .load(anyProjectDetails.projectLogoFilePath)
                .placeholder(com.innoprog.android.R.drawable.ic_placeholder_logo)
                .centerCrop()
                .transform(RoundedCorners(radius))
                .into(ivProjectLogo)

            tvProjectName.text = anyProjectDetails.name
            tvProjectDirection.text = anyProjectDetails.area
        }
    }
}
