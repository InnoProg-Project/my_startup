package com.innoprog.android.feature.training.course_information.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentCourseInformationBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.training.common.VerticalSpaceDecorator
import com.innoprog.android.feature.training.course_information.di.DaggerCourseInformationComponent
import com.innoprog.android.feature.training.course_information.presentation.model.CourseInformationState
import com.innoprog.android.feature.training.training_list.presentation.TrainingListFragment.Companion.COURSE_KEY
import com.innoprog.android.uikit.ImageLoadingType
import com.innoprog.android.uikit.R

class CourseInformationFragment : BaseFragment<FragmentCourseInformationBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<CourseInformationViewModel>()
    override fun diComponent(): ScreenComponent = DaggerCourseInformationComponent.builder().build()

    private val courseId by lazy { arguments?.getInt(COURSE_KEY) }

    private var videoAdapter: VideoAdapter? = null
    private var documentAdapter: DocumentRecyclerViewAdapter? = null
    private val decorator: VerticalSpaceDecorator by lazy {
        VerticalSpaceDecorator(resources.getDimensionPixelSize(R.dimen.margin_8))
    }

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCourseInformationBinding {
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
        binding.courseInformationVideoRV.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        //TODO
        // Добавить onClickListener
        videoAdapter = VideoAdapter {}
        binding.courseInformationVideoRV.addItemDecoration(decorator)
        binding.courseInformationVideoRV.adapter = videoAdapter
    }

    private fun initDocumentsRecyclerView() {
        binding.courseInformationDocumentsRV.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        documentAdapter = DocumentRecyclerViewAdapter { url ->
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
        binding.courseInformationDocumentsRV.addItemDecoration(decorator)
        binding.courseInformationDocumentsRV.adapter = documentAdapter
    }

    private fun render(state: CourseInformationState) {
        when (state) {
            is CourseInformationState.Content -> {
                Glide.with(requireContext())
                    .load(state.courseInformation.courseLogoURL)
                    .into(binding.courseLogo)

                binding.courseInformationTitle.text = state.courseInformation.courseTitle
                binding.courseInformationDescription.text = state.courseInformation.courseDescription

                val avatarUrl = state.courseInformation.courseAuthorAvatarURL
                val placeholderResId = R.drawable.ic_person
                val imageType =
                    ImageLoadingType.ImageNetwork(avatarUrl, placeholderResId = placeholderResId)
                binding.courseInformationAuthorAvatar.loadImage(imageType)

                binding.courseInformationAuthorName.text = state.courseInformation.courseAuthorName
                binding.courseInformationAuthorPosition.text = state.courseInformation.courseAuthorPosition
                binding.courseInformationDate.text = state.courseInformation.courseDate
                binding.courseInformationDirection.text = state.courseInformation.courseDirection

                if (state.courseInformation.videos != null) {
                    binding.courseInformationVideoTitle.visibility = View.VISIBLE
                    binding.courseInformationVideoRV.visibility = View.VISIBLE
                    videoAdapter?.items = state.courseInformation.videos
                } else {
                    binding.courseInformationVideoTitle.visibility = View.INVISIBLE
                    binding.courseInformationVideoRV.visibility = View.INVISIBLE
                }

                if (state.courseInformation.documents != null) {
                    binding.courseInformationDocumentsTitle.visibility = View.VISIBLE
                    binding.courseInformationDocumentsRV.visibility = View.VISIBLE
                    documentAdapter?.items = state.courseInformation.documents
                } else {
                    binding.courseInformationDocumentsTitle.visibility = View.INVISIBLE
                    binding.courseInformationDocumentsRV.visibility = View.INVISIBLE
                }
            }

            is CourseInformationState.Error -> Unit
            is CourseInformationState.Load -> Unit
        }
    }
}
