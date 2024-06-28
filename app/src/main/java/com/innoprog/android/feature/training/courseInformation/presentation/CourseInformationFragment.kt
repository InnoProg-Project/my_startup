package com.innoprog.android.feature.training.courseInformation.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentCourseInformationBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.training.common.VerticalSpaceDecorator
import com.innoprog.android.feature.training.courseInformation.di.DaggerCourseInformationComponent
import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformationModel
import com.innoprog.android.uikit.ImageLoadingType
import com.innoprog.android.uikit.R

class CourseInformationFragment : BaseFragment<FragmentCourseInformationBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<CourseInformationViewModel>()
    override fun diComponent(): ScreenComponent = DaggerCourseInformationComponent.builder().build()

    private val courseId by lazy {
        arguments?.let { args ->
            CourseInformationFragmentArgs.fromBundle(args).courseId
        }
    }

    private var videoAdapter: VideoAdapter? = null
    private var documentAdapter: DocumentRecyclerViewAdapter? = null
    private val decorator: VerticalSpaceDecorator by lazy {
        VerticalSpaceDecorator(resources.getDimensionPixelSize(R.dimen.margin_8))
    }

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentCourseInformationBinding {
        return FragmentCourseInformationBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }

        binding.courseInformationTopBar.setLeftIconClickListener {
            viewModel.navigateUp()
        }

        initVideoRecyclerView()
        initDocumentsRecyclerView()
        courseId?.let { viewModel.getCourseInformation(it) }
    }

    private fun initVideoRecyclerView() {
        videoAdapter = VideoAdapter(requireContext()) {
            viewModel.navigateTo(
                com.innoprog.android.R.id.videoPlayerFragment, bundleOf(VIDEO_PLAYER_KEY to it)
            )
        }
        binding.courseInformationVideoRV.addItemDecoration(decorator)
        binding.courseInformationVideoRV.adapter = videoAdapter
    }

    private fun initDocumentsRecyclerView() {
        documentAdapter = DocumentRecyclerViewAdapter { url ->
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
        binding.courseInformationDocumentsRV.addItemDecoration(decorator)
        binding.courseInformationDocumentsRV.adapter = documentAdapter
    }

    private fun render(state: CourseInformationState) {
        when (state) {
            is CourseInformationState.Content -> showContent(state.courseInformation)
            is CourseInformationState.Error -> Unit
            is CourseInformationState.Load -> Unit
        }
    }

    private fun showContent(courseInformation: CourseInformationModel) {
        Glide.with(requireContext()).load(courseInformation.courseLogoURL).into(binding.courseLogo)
        val avatarUrl = courseInformation.courseAuthorAvatarURL
        val placeholderResId = R.drawable.ic_person
        val imageType =
            ImageLoadingType.ImageNetwork(avatarUrl, placeholderResId = placeholderResId)
        binding.courseInformationAuthorAvatar.loadImage(imageType)
        binding.courseInformationAuthorName.text = courseInformation.courseAuthorName
        binding.courseInformationAuthorPosition.text = courseInformation.courseAuthorPosition
        binding.courseInformationDate.text = courseInformation.courseDate
        binding.courseInformationDirection.text = courseInformation.courseDirection
        binding.courseInformationTitle.text = courseInformation.courseTitle
        binding.courseInformationDescription.text = courseInformation.courseDescription

        if (courseInformation.videos != null) {
            binding.courseInformationVideoTitle.visibility = View.VISIBLE
            binding.courseInformationVideoRV.visibility = View.VISIBLE
            videoAdapter?.items = courseInformation.videos
        } else {
            binding.courseInformationVideoTitle.visibility = View.INVISIBLE
            binding.courseInformationVideoRV.visibility = View.INVISIBLE
        }

        if (courseInformation.documents != null) {
            binding.courseInformationDocumentsTitle.visibility = View.VISIBLE
            binding.courseInformationDocumentsRV.visibility = View.VISIBLE
            documentAdapter?.items = courseInformation.documents
        } else {
            binding.courseInformationDocumentsTitle.visibility = View.INVISIBLE
            binding.courseInformationDocumentsRV.visibility = View.INVISIBLE
        }
    }

    companion object {
        const val VIDEO_PLAYER_KEY = "VIDEO_PLAYER_KEY"
    }
}
