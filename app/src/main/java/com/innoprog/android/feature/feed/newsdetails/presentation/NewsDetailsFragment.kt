package com.innoprog.android.feature.feed.newsdetails.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.tabs.TabLayoutMediator
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentNewsDetailsBinding
import com.innoprog.android.di.AppComponentHolder
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.feed.newsdetails.di.DaggerNewsDetailsComponent
import com.innoprog.android.feature.feed.newsdetails.domain.models.CommentModel
import com.innoprog.android.feature.feed.newsdetails.domain.models.NewsDetailsModel
import com.innoprog.android.feature.feed.newsfeed.domain.models.Project
import com.innoprog.android.feature.imagegalleryadapter.ImageGalleryAdapter
import com.innoprog.android.util.ErrorScreenState
import com.innoprog.android.util.Resource
import okhttp3.internal.format

open class NewsDetailsFragment : BaseFragment<FragmentNewsDetailsBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<NewsDetailsViewModel>()
    private var newsId: String = ""
    private var galleryAdapter: ImageGalleryAdapter? = null
    private var commentsList: ArrayList<CommentModel> = arrayListOf()
    private val commentsAdapter: CommentsAdapter by lazy {
        CommentsAdapter(
            commentsList,
            { comment -> onCommentClick(comment) },
            { comment -> onDeleteClick(comment) }
        )
    }

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

        viewModel.screenState.observe(viewLifecycleOwner) {
            updateUI(it)
        }

        val args: NewsDetailsFragmentArgs by navArgs()
        newsId = args.newsId

        viewModel.getNewsDetails(newsId)

        viewModel.addCommentResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Success -> {
                    commentsList.add(result.data)
                    commentsAdapter.notifyItemInserted(commentsList.size - 1)
                    binding.inputComment.setText("")
                    binding.tvComments.text = format(getString(R.string.comments), commentsList.size)
                    hideKeyboard()
                }

                is Resource.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Ошибка добавления комментария",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {}
            }
        }
    }

    private fun setUiListeners() {
        binding.apply {
            newsTopBar.setLeftIconClickListener {
                viewModel.navigateUp()
            }

            newsTopBar.setRightIconClickListener {
                Toast.makeText(
                    requireContext(),
                    "Добавлено/удалено из избранного",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            btnShowAll.setOnClickListener {
                if (tvPublicationContent.maxLines == TV_MAX_LINES) {
                    tvPublicationContent.maxLines = Int.MAX_VALUE
                    btnShowAll.isVisible = false
                }
            }

            projectCard.setOnClickListener {
                findNavController().navigate(R.id.action_newsDetailsFragment_to_projectFragment)
            }

            inputComment.setRightIconClickListener {
                val content = inputComment.getText()
                if (content.isNotBlank()) {
                    viewModel.addComment(newsId, content)
                } else {
                    Toast.makeText(requireContext(), R.string.empty_comment, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun initImageGallery(images: List<String>?) {
        galleryAdapter = images?.let { ImageGalleryAdapter(it) }
        binding.viewPager.adapter = galleryAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position -> }.attach()
    }

    private fun updateUI(state: NewsDetailsScreenState) {
        when (state) {
            is NewsDetailsScreenState.Loading -> showLoading()
            is NewsDetailsScreenState.Content -> showContent(state.newsDetails, state.comments)
            is NewsDetailsScreenState.Error -> showError(state.errorType)
        }
    }

    private fun showLoading() {
        binding.apply {
            nsvDetails.isVisible = false
            layoutErrorScreen.isVisible = false
            circularProgress.isVisible = true
        }
    }

    private fun showError(errorState: ErrorScreenState) {
        binding.apply {
            nsvDetails.isVisible = false
            circularProgress.isVisible = false
            fetchErrorScreen(errorState)
            layoutErrorScreen.isVisible = true
        }
    }

    private fun fetchErrorScreen(errorState: ErrorScreenState) {
        val errorImageRes = errorState.imageResource
        val errorTextRes = errorState.messageResource
        binding.layoutErrorScreen.apply {
            findViewById<ImageView>(com.innoprog.android.uikit.R.id.iv_error_image)
                .setImageResource(errorImageRes)
            findViewById<TextView>(com.innoprog.android.uikit.R.id.tv_error_message)
                .setText(errorTextRes)
        }
    }

    open fun showContent(newsDetails: NewsDetailsModel, comments: List<CommentModel>) {
        binding.apply {
            layoutErrorScreen.isVisible = false
            circularProgress.isVisible = false
            nsvDetails.isVisible = true
            initImageGallery(newsDetails.coverUrl)
            btnShowAll.isVisible = tvPublicationContent.maxLines > TV_MAX_LINES
            tvPublicationTitle.text = newsDetails.title
            tvPublicationContent.text = newsDetails.content
            tvNewsComments.text = newsDetails.commentsCount.toString()
            newsLikesView.setLikeCount(newsDetails.likesCount)
            tvNewsPublicationDate.text = getFormattedDate(newsDetails.publishedAt)
            tvNewsAuthorName.text = newsDetails.author.name

            val newsAuthorPosition =
                StringBuilder().append(newsDetails.author.company.role).append(" в ")
                    .append(newsDetails.author.company.companyName)
                    .toString()
            tvNewsAuthorPosition.text = newsAuthorPosition

            newsDetails.project?.let { project ->
                loadProjectInfo(newsDetails.type, project)
            }

            tvComments.text = format(getString(R.string.comments), newsDetails.commentsCount)

            if (comments.isEmpty()) {
                tvNoCommentsPlaceholder.isVisible = true
                rvComments.isVisible = false
            } else {
                tvNoCommentsPlaceholder.isVisible = false
                rvComments.isVisible = true
                updateRecyclerView(comments)
                rvComments.adapter = commentsAdapter
            }
        }
    }

    private fun updateRecyclerView(comments: List<CommentModel>) {
        commentsList.clear()
        commentsList.addAll(comments)
        commentsAdapter.notifyDataSetChanged()
    }

    private fun onCommentClick(comment: CommentModel) {
        val position = commentsList.indexOf(comment)
        if (position != -1) {
            comment.isClicked = !comment.isClicked
            commentsAdapter.updateItem(position)
        }
    }

    private fun onDeleteClick(comment: CommentModel) {
        Toast.makeText(
            requireContext(),
            "Удаляем комментарий: ${comment.commentContent}",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun loadProjectInfo(type: String, project: Project) {
        val radius = binding.root.resources.getDimensionPixelSize(R.dimen.corner_radius_10)
        binding.apply {
            if (type == "NEWS") {
                tvAboutProjectTitle.isVisible = true
                projectCard.isVisible = true
                Glide
                    .with(requireContext())
                    .load(project.logoUrl)
                    .placeholder(R.drawable.ic_placeholder_logo)
                    .centerCrop()
                    .transform(RoundedCorners(radius))
                    .into(ivProjectLogo)
                tvProjectName.text = project.name
                tvProjectDirection.text = project.area
            } else {
                tvAboutProjectTitle.isVisible = false
                projectCard.isVisible = false
            }
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(binding.inputComment.windowToken, 0)
    }

    companion object {
        const val TV_MAX_LINES = 6
        const val MIN_WIDTH = 0
        const val MAX_WIDTH = 9
    }
}
