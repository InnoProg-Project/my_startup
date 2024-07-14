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
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
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
import com.innoprog.android.feature.feed.newsfeed.domain.models.PublicationType
import com.innoprog.android.feature.feed.userprojectscreen.presentation.UserProjectDetailsFragment
import com.innoprog.android.feature.imagegalleryadapter.ImageGalleryAdapter
import com.innoprog.android.uikit.InnoProgButtonView
import com.innoprog.android.util.ErrorScreenState
import com.innoprog.android.util.Resource
import com.innoprog.android.util.debounceUnitFun
import okhttp3.internal.format

open class NewsDetailsFragment : BaseFragment<FragmentNewsDetailsBinding, BaseViewModel>() {
    override val viewModel by injectViewModel<NewsDetailsViewModel>()
    private val galleryAdapter = ImageGalleryAdapter()
    private var newsId: String = ""
    private val debounceNavigateTo = debounceUnitFun<Fragment?>(lifecycleScope)
    private var commentsList: ArrayList<CommentModel> = arrayListOf()
    private val commentsAdapter: CommentsAdapter by lazy {
        CommentsAdapter(
            commentsList = commentsList,
            onCommentClick = { comment -> onCommentClick(comment) },
            onDeleteClick = { comment -> onDeleteClick(comment) }
        )
    }

    override fun diComponent(): ScreenComponent {
        return DaggerNewsDetailsComponent.builder()
            .appComponent(AppComponentHolder.getComponent())
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
        viewModel.screenState.observe(viewLifecycleOwner) { updateUI(it) }
        viewModel.getShowProjectTrigger().observe(viewLifecycleOwner) { openProject(it) }

        val args: NewsDetailsFragmentArgs by navArgs()
        newsId = args.newsId

        viewModel.getNewsDetails(newsId)

        viewModel.addCommentResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Success -> {
                    commentsList.add(0, result.data)
                    commentsAdapter.notifyItemInserted(0)
                    commentsAdapter.notifyItemRangeChanged(1, commentsList.size - 1)
                    binding.inputComment.setText("")
                    binding.tvComments.text = format(getString(R.string.comments), commentsList.size)
                    hideKeyboard()
                }

                is Resource.Error -> {
                    showToast("Ошибка добавления комментария")
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
                showToast("Добавлено/удалено из избранного")
            }

            btnShowAll.setOnClickListener {
                if (tvPublicationContent.maxLines == TV_MAX_LINES) {
                    tvPublicationContent.maxLines = Int.MAX_VALUE
                    btnShowAll.isVisible = false
                }
            }

            projectCard.setOnClickListener {
                debounceNavigateTo(this@NewsDetailsFragment) { _ ->
                    findNavController().navigate(R.id.action_newsDetailsFragment_to_projectFragment)
                }
            }

            inputComment.setRightIconClickListener {
                val content = inputComment.getText()
                if (content.isNotBlank()) {
                    viewModel.addComment(newsId, content)
                } else {
                    showToast(getString(R.string.empty_comment))
                }
            }
        }
    }

    private fun openProject(projectId: String) {
        val bundle = Bundle().apply {
            putString(UserProjectDetailsFragment.USER_PROJECT_DETAILS, projectId)
            putBoolean(UserProjectDetailsFragment.CUSTOM_PROJECT, false)
        }
        viewModel.navigateTo(
            R.id.action_newsDetailsFragment_to_projectFragment,
            bundle
        )
    }

    private fun initImageGallery(images: List<Any>) {
        galleryAdapter.setImageList(images)
        binding.viewPager.adapter = galleryAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()
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
            findViewById<InnoProgButtonView>(com.innoprog.android.uikit.R.id.ipbtn_repeat_request)
                .setOnClickListener { viewModel.getNewsDetails(newsId) }
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
        showToast("Удаляем комментарий: ${comment.commentContent}")
    }

    private fun loadProjectInfo(type: String, project: Project) {
        val radius = binding.root.resources.getDimensionPixelSize(R.dimen.corner_radius_10)
        binding.apply {
            if (type == PublicationType.NEWS.value) {
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

    private fun showToast(message: String) {
        context?.let { Toast.makeText(it, message, Toast.LENGTH_SHORT).show() }
    }

    private companion object {
        const val TV_MAX_LINES = 6
    }
}
