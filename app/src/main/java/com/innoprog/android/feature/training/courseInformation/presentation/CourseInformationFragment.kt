package com.innoprog.android.feature.training.courseInformation.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentCourseInformationBinding
import com.innoprog.android.di.AppComponentHolder
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.training.common.VerticalSpaceDecorator
import com.innoprog.android.feature.training.courseInformation.di.DaggerCourseInformationComponent
import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformation
import com.innoprog.android.uikit.InnoProgButtonView
import com.innoprog.android.uikit.R
import com.innoprog.android.util.ErrorScreenState

class CourseInformationFragment : BaseFragment<FragmentCourseInformationBinding, BaseViewModel>() {
    override val viewModel by injectViewModel<CourseInformationViewModel>()

    override fun diComponent(): ScreenComponent {
        val appComponent = AppComponentHolder.getComponent()
        return DaggerCourseInformationComponent.builder()
            .appComponent(appComponent)
            .build()
    }

    private val courseId by lazy {
        arguments?.let { args ->
            CourseInformationFragmentArgs.fromBundle(args).courseId
        } ?: ""
    }

    private var videoAdapter: VideoAdapter? = null
    private var documentAdapter: DocumentRecyclerViewAdapter? = null
    private val decorator: VerticalSpaceDecorator by lazy {
        VerticalSpaceDecorator(resources.getDimensionPixelSize(R.dimen.margin_8))
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
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
        if (courseId.isNotEmpty()) {
            viewModel.getCourseInformation(courseId)
        }
    }

    private fun initVideoRecyclerView() {
        videoAdapter = VideoAdapter {
            viewModel.navigateTo(
                com.innoprog.android.R.id.videoPlayerFragment,
                bundleOf(VIDEO_PLAYER_KEY to it)
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
            is CourseInformationState.Content -> {
                binding.progress.hide()
                binding.courseInformation.visibility = View.VISIBLE
                binding.errorScreen.visibility = View.GONE
                installAttributes(state.courseInformation)
            }

            is CourseInformationState.Error -> renderError(state.errorType)
            is CourseInformationState.Load -> {
                binding.progress.show()
                binding.errorScreen.visibility = View.GONE
                binding.courseInformation.visibility = View.INVISIBLE
            }
        }
    }

    private fun installAttributes(courseInformation: CourseInformation) {
        if (courseInformation.imageList.isEmpty()) {
            binding.courseLogo.visibility = View.GONE
        } else {
            binding.courseLogo.visibility = View.VISIBLE
            Glide.with(requireContext())
                .load(courseInformation.imageList)
                .into(binding.courseLogo)
        }

        binding.courseInformationTitle.text = courseInformation.title
        binding.courseInformationDescription.text = courseInformation.description
        binding.courseInformationAuthorAvatar.text =
            viewModel.formatAuthorName(courseInformation.authorName)
        binding.courseInformationAuthorName.text = courseInformation.authorName
        binding.courseInformationDate.text = courseInformation.createdDate
        binding.courseInformationDirection.text = courseInformation.direction

        if (courseInformation.videoList.isNotEmpty()) {
            binding.courseInformationVideoTitle.visibility = View.VISIBLE
            binding.courseInformationVideoRV.visibility = View.VISIBLE
            videoAdapter?.setVideoList(courseInformation.videoList)
        } else {
            binding.courseInformationVideoTitle.visibility = View.GONE
            binding.courseInformationVideoRV.visibility = View.GONE
        }

        if (courseInformation.documentList.isNotEmpty()) {
            binding.courseInformationDocumentsTitle.visibility = View.VISIBLE
            binding.courseInformationDocumentsRV.visibility = View.VISIBLE
            documentAdapter?.setDocumentList(courseInformation.documentList)
        } else {
            binding.courseInformationDocumentsTitle.visibility = View.GONE
            binding.courseInformationDocumentsRV.visibility = View.GONE
        }
    }

    private fun renderError(errorState: ErrorScreenState) = with(binding) {
        courseInformation.visibility = View.GONE
        progress.visibility = View.GONE
        if (errorState == ErrorScreenState.UNAUTHORIZED) {
            viewModel.clearBackStackAndNavigateToAuthorization()
        } else {
            fetchErrorScreen(errorState)
            errorScreen.visibility = View.VISIBLE
        }
    }

    private fun fetchErrorScreen(errorState: ErrorScreenState) {
        val errorImageRes = errorState.imageResource
        val errorTextRes = errorState.messageResource
        binding.errorScreen.apply {
            findViewById<ImageView>(R.id.iv_error_image)
                .setImageResource(errorImageRes)
            findViewById<TextView>(R.id.tv_error_message)
                .setText(errorTextRes)
            findViewById<InnoProgButtonView>(R.id.ipbtn_repeat_request).setOnClickListener {
                viewModel.getCourseInformation(courseId)
            }
        }
    }

    companion object {
        const val VIDEO_PLAYER_KEY = "VIDEO_PLAYER_KEY"
    }
}
