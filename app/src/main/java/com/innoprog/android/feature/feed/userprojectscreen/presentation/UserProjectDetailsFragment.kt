package com.innoprog.android.feature.feed.userprojectscreen.presentation

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.tabs.TabLayoutMediator
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.databinding.FragmentAnyProjectDetailsBinding
import com.innoprog.android.di.AppComponentHolder
import com.innoprog.android.feature.feed.anyProjectDetails.domain.models.AnyProjectDetailsModel
import com.innoprog.android.feature.feed.anyProjectDetails.domain.models.DocumentModel
import com.innoprog.android.feature.feed.anyProjectDetails.presentation.DocumentAdapter
import com.innoprog.android.feature.feed.userprojectscreen.di.DaggerUserProjectDetailsComponent
import com.innoprog.android.feature.imagegalleryadapter.ImageGalleryAdapter
import com.innoprog.android.feature.training.common.VerticalSpaceDecorator
import com.innoprog.android.uikit.R
import com.innoprog.android.util.ErrorScreenState
import kotlinx.coroutines.launch
import java.util.Locale

/**
 * Фрагмент получает id через bundle и должен отправлять запрос в api
 */
class UserProjectDetailsFragment :
    BaseFragment<FragmentAnyProjectDetailsBinding, UserProjectViewModel>() {

    override val viewModel by injectViewModel<UserProjectViewModel>()
    private val projectId: String? by lazy {
        arguments?.getString(USER_PROJECT_DETAILS)
    }

    override fun diComponent() = DaggerUserProjectDetailsComponent
        .builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    private val documentAdapter = DocumentAdapter { uri ->
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
    }
    private val galleryAdapter = ImageGalleryAdapter()

    private val decorator: VerticalSpaceDecorator by lazy {
        VerticalSpaceDecorator(resources.getDimensionPixelSize(R.dimen.margin_16))
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAnyProjectDetailsBinding {
        return FragmentAnyProjectDetailsBinding.inflate(inflater, container, false)
    }

    override fun subscribe() {
        viewModel.getProjectDetails(projectId)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.observe(viewLifecycleOwner) { state ->
                renderState(state)
            }
        }
        clickListeners()
    }

    private fun renderState(state: UserProjectDetailsState) {
        when (state) {
            is UserProjectDetailsState.Content -> fetchData(state.project)
            is UserProjectDetailsState.Empty -> showEmpty()
            is UserProjectDetailsState.Loading -> showLoading()
            is UserProjectDetailsState.Error -> handleError(state.errorType)
        }
    }

    private fun fetchData(details: AnyProjectDetailsModel) = with(binding) {
        showContent()
        initImageGallery()
        loadProjectInfo(details)
        if (details.documents != null) {
            val documentsList = details.documents
            initDocumentsRecyclerView(documentsList)
        }
        tvShortDescription.text = details.shortDescription
        tvProjectDescription.text = details.description
        tvFinancingStageValue.text = details.financingStage
        tvDeadlineValue.text = formatDate(details.deadline)
        tvLinkToWebValue.text = details.siteUrls[0]
        tvLinkToAppValue.text = details.siteUrls[1]
        tvLinkToSocialNetworkValue.text = details.siteUrls[2]
    }

    private fun showContent() = with(binding) {
        nsvProjectDetailsBody.isVisible = true
        circularProgress.isVisible = false
        layoutErrorScreen.isVisible = false
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

    private fun initDocumentsRecyclerView(documentsList: List<DocumentModel>) {
        binding.rvDocuments.addItemDecoration(decorator)
        binding.rvDocuments.adapter = documentAdapter
        documentAdapter.setItems(documentsList)
    }

    private fun initImageGallery() {
        galleryAdapter.setImageList(imageList)
        binding.viewPager.adapter = galleryAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()
    }

    private fun showEmpty() = with(binding) {
        nsvProjectDetailsBody.isVisible = false
        layoutErrorScreen.isVisible = true
        circularProgress.isVisible = false
    }


    private fun showLoading() = with(binding) {
        nsvProjectDetailsBody.isVisible = false
        layoutErrorScreen.isVisible = false
        circularProgress.isVisible = true
    }

    private fun handleError(errorState: ErrorScreenState) = with(binding) {
        circularProgress.isVisible = false
        nsvProjectDetailsBody.isVisible = false

        if (errorState == ErrorScreenState.UNAUTHORIZED) {
            viewModel.clearBackStackAndNavigateToAuthorization()
        } else {
            layoutErrorScreen.isVisible = true
            fetchErrorScreen(errorState)
        }
    }

    private fun fetchErrorScreen(errorState: ErrorScreenState) {
        val errorImageRes = errorState.imageResource
        val errorTextRes = errorState.messageResource
        binding.layoutErrorScreen.apply {
            findViewById<ImageView>(R.id.iv_error_image)
                .setImageResource(errorImageRes)
            findViewById<TextView>(R.id.tv_error_message)
                .setText(errorTextRes)
        }
    }

    private fun clickListeners() = with(binding) {
        listOf(
            ibtnvEditDescription,
            ibtnvEditDocuments,
            ivEditIcon,
            ibtnvEditAdditionalInfo,
            tvProjectDirection
        ).forEach {
            it.setOnClickListener(onClickListener())
        }

        topBar.setLeftIconClickListener {
            viewModel.navigateUp()
        }
    }

    private fun onClickListener() = View.OnClickListener {
        with(binding) {
            when (it) {
                ibtnvEditDescription -> Log.i(USER_PROJECT_TAG, "to step 1")
                ivEditIcon -> Log.i(USER_PROJECT_TAG, "to step 2")
                tvProjectDirection -> Log.i(USER_PROJECT_TAG, "to step 3")
                ibtnvEditDocuments -> Log.i(USER_PROJECT_TAG, "to step 4")
                ibtnvEditAdditionalInfo -> Log.i(USER_PROJECT_TAG, "to step 5")
            }
        }
    }

    private fun formatDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
        val date = inputFormat.parse(inputDate)
        return outputFormat.format(date)
    }

    companion object {
        const val USER_PROJECT_DETAILS = "user_project_details"
        const val CUSTOM_PROJECT = "custom_project"
        private val USER_PROJECT_TAG = UserProjectDetailsFragment::class.java.simpleName
    }
}

/**
 * Хардкод пока не подключим апи
 */
@Suppress("Detekt.MaxLineLength")
val imageList = listOf(
    "https://images.unsplash.com/photo-1718585708744-573c54a2c38c?q=80&w=3087&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1719216325263-9070d79336c3?q=80&w=2828&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1718930928057-09072e3b511d?q=80&w=2744&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1719216324560-523fc4ddb8b9?q=80&w=2828&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1719328376616-463c69404f0f?q=80&w=3088&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
)