package com.innoprog.android.feature.training.courseInformation.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentCourseInformationBinding
import com.innoprog.android.di.AppComponentHolder
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.training.common.VerticalSpaceDecorator
import com.innoprog.android.feature.training.courseInformation.di.DaggerCourseInformationComponent
import com.innoprog.android.uikit.R

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
        courseId?.let { viewModel.getCourseInformation(it) }
    }

    private fun initVideoRecyclerView() {
        videoAdapter = VideoAdapter(requireContext()) {
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
                Log.e("render", "${state.courseInformation.attachments}")

                Glide.with(requireContext())
                    .load(viewModel.getImage(state.courseInformation.attachments))
                    .into(binding.courseLogo)

                binding.courseInformationTitle.text = state.courseInformation.title
                binding.courseInformationDescription.text =
                    state.courseInformation.description

                //val avatarUrl = state.courseInformation.courseAuthorAvatarURL
                val placeholderResId = R.drawable.ic_person
                //val imageType =
                //    ImageLoadingType.ImageNetwork(avatarUrl, placeholderResId = placeholderResId)
                //binding.courseInformationAuthorAvatar.loadImage(imageType)

                binding.courseInformationAuthorName.text = state.courseInformation.authorName
                //binding.courseInformationAuthorPosition.text =
                //    state.courseInformation.courseAuthorPosition
                binding.courseInformationDate.text = state.courseInformation.createdDate
                binding.courseInformationDirection.text = state.courseInformation.direction
                val videos = viewModel.getVideo(state.courseInformation.attachments)
                Log.e("videos", "$videos")
                if (videos != null) {
                    binding.courseInformationVideoTitle.visibility = View.VISIBLE
                    binding.courseInformationVideoRV.visibility = View.VISIBLE
                    Log.e("videos!!!!!!", "$videos")
                    videoAdapter?.items = videos
                } else {
                    binding.courseInformationVideoTitle.visibility = View.INVISIBLE
                    binding.courseInformationVideoRV.visibility = View.INVISIBLE
                }

                val documents = viewModel.getDocument(state.courseInformation.attachments)
                if (documents != null) {
                    binding.courseInformationDocumentsTitle.visibility = View.VISIBLE
                    binding.courseInformationDocumentsRV.visibility = View.VISIBLE
                    documentAdapter?.items = documents
                } else {
                    binding.courseInformationDocumentsTitle.visibility = View.INVISIBLE
                    binding.courseInformationDocumentsRV.visibility = View.INVISIBLE
                }
            }

            is CourseInformationState.Error -> Unit
            is CourseInformationState.Load -> Unit
        }
    }

    companion object {

        const val VIDEO_PLAYER_KEY = "VIDEO_PLAYER_KEY"
    }
}
