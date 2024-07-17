package com.innoprog.android.feature.feed.anyProjectDetails.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
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
import com.innoprog.android.feature.feed.anyProjectDetails.domain.models.DocumentModel
import com.innoprog.android.feature.imagegalleryadapter.ImageGalleryAdapter
import com.innoprog.android.feature.training.common.VerticalSpaceDecorator
import com.innoprog.android.uikit.R

class AnyProjectDetailsFragment : BaseFragment<FragmentAnyProjectDetailsBinding, BaseViewModel>() {
    override val viewModel by injectViewModel<AnyProjectDetailsViewModel>()
    private val galleryAdapter = ImageGalleryAdapter()
    private val documentAdapter = DocumentAdapter { url ->
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
    private val decorator by lazy {
        VerticalSpaceDecorator(resources.getDimensionPixelSize(R.dimen.margin_16))
    }

    override fun diComponent(): ScreenComponent {
        return DaggerAnyProjectDetailsComponent.builder()
            .appComponent(AppComponentHolder.getComponent())
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
        hideUnusedItems()
        setUiListeners()
        val args: AnyProjectDetailsFragmentArgs by navArgs()
        val projectId = args.projectId

        viewModel.screenState.observe(viewLifecycleOwner) {
            updateUI(it)
        }

        viewModel.getAnyProjectDetails(projectId)
    }

    private fun setUiListeners() {
        binding.topBar.setLeftIconClickListener { viewModel.navigateUp() }
    }

    private fun initImageGallery(anyProjectDetails: AnyProjectDetailsModel) {
        val images = anyProjectDetails.images.map { it.filePath }
        galleryAdapter.setImageList(images)
        binding.viewPager.adapter = galleryAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()
    }

    private fun initDocumentsRecyclerView(documentsList: List<DocumentModel>) {
        binding.rvDocuments.addItemDecoration(decorator)
        binding.rvDocuments.adapter = documentAdapter
        documentAdapter.setItems(documentsList)
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
            initImageGallery(anyProjectDetails)
            loadProjectInfo(anyProjectDetails)
            if (anyProjectDetails.documents.isNotEmpty()) {
                val documentsList = anyProjectDetails.documents
                initDocumentsRecyclerView(documentsList)
            }
            tvShortDescription.text = anyProjectDetails.shortDescription
            tvProjectDirection.text = anyProjectDetails.description
            tvFinancingStageValue.text = anyProjectDetails.financingStage
            tvDeadlineValue.text = anyProjectDetails.deadline
            tvLinkToWebValue.text = anyProjectDetails.siteUrls
            tvLinkToAppValue.isVisible = false
            tvLinkToApp.isVisible = false
            tvLinkToSocialNetwork.isVisible = false
            tvLinkToSocialNetworkValue.isVisible = false
        }
    }

    private fun loadProjectInfo(anyProjectDetails: AnyProjectDetailsModel) {
        val radius =
            binding.root.resources.getDimensionPixelSize(com.innoprog.android.R.dimen.corner_radius_8)
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

    private fun hideUnusedItems() = with(binding) {
        listOf(
            ibtnvEditDescription,
            ibtnvEditDocuments,
            ivEditIcon,
            ibtnvEditAdditionalInfo
        ).forEach {
            it.visibility = View.GONE
        }
    }
}
