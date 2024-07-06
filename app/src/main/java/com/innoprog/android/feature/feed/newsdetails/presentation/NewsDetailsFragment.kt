package com.innoprog.android.feature.feed.newsdetails.presentation

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.innoprog.android.feature.imagegalleryadapter.ImageGalleryAdapter
import okhttp3.internal.format
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

open class NewsDetailsFragment : BaseFragment<FragmentNewsDetailsBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<NewsDetailsViewModel>()
    private var galleryAdapter: ImageGalleryAdapter? = null
    private var commentsAdapter: CommentsAdapter? = null

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
        val newsId = args.newsId

        viewModel.getNewsDetails(newsId)
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
        }
    }

    private fun initImageGallery() {
        val images = listOf(
            R.drawable.news_sample,
            R.drawable.course_logo_sample,
            R.drawable.news_sample,
            R.drawable.course_logo_sample,
            R.drawable.news_sample,
        )

        galleryAdapter = ImageGalleryAdapter(images)
        binding.viewPager.adapter = galleryAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position -> }.attach()
    }

    private fun updateUI(state: NewsDetailsScreenState) {
        when (state) {
            is NewsDetailsScreenState.Loading -> showLoading()
            is NewsDetailsScreenState.Content -> state.newsDetails?.let { showContent(it) }
            is NewsDetailsScreenState.Error -> showError()
        }
    }

    private fun showLoading() {
        Toast.makeText(requireContext(), "Загрузка", Toast.LENGTH_SHORT).show()
    }

    private fun showError() {
        Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
    }

    open fun showContent(newsDetails: NewsDetailsModel) {
        binding.apply {
            initImageGallery()

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

            loadProjectInfo(newsDetails)

            tvComments.text = format(getString(R.string.comments), newsDetails.commentsCount)

            if (newsDetails.comments != null) {
                rvComments.isVisible = true
                val commentsList = newsDetails.comments
                initRecyclerView(commentsList)
            } else {
                rvComments.isVisible = false
                tvNoCommentsPlaceholder.isVisible = true
            }
        }
    }

    private fun getFormattedDate(inputDate: String): String {
        val inputFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy в HH:mm", Locale("ru"))
        val dateTime = LocalDateTime.parse(inputDate, inputFormatter)
        return dateTime.format(outputFormatter)
    }

    private fun initRecyclerView(commentsList: List<CommentModel>) {
        commentsAdapter =
            CommentsAdapter(
                commentsList,
                object : CommentsAdapter.OnClickListener {
                    override fun onItemClick(
                        position: Int,
                        comment: CommentModel,
                        context: Context
                    ) {
                        val itemView =
                            binding.rvComments.layoutManager?.findViewByPosition(position)
                        itemView?.setBackgroundColor(Color.parseColor("#F0F0F0"))
                        itemView?.findViewById<TextView>(R.id.tvDeleteComment)?.visibility =
                            View.VISIBLE
                    }
                }
            )

        binding.rvComments.adapter = commentsAdapter
    }

    @Suppress("Detekt.Indentation")
    private fun loadProjectInfo(newsDetails: NewsDetailsModel) {
        val radius = binding.root.resources.getDimensionPixelSize(R.dimen.corner_radius_10)
        binding.apply {
            if (newsDetails.type == "project") {
                tvAboutProjectTitle.isVisible = true
                projectCard.isVisible = true
                Glide
                    .with(requireContext())
                    .load(
                        "https://img.freepik.com/free-vector/ai-technology-microchip-" +
                            "background-vector-digital-transformation-concept_53876-112222.jpg"
                    )
                    .placeholder(R.drawable.ic_placeholder_logo)
                    .centerCrop()
                    .transform(RoundedCorners(radius))
                    .into(ivProjectLogo)

                tvProjectName.text = "Искусственный интеллект"
                tvProjectDirection.text = "Искусственный интеллект"
            } else {
                tvAboutProjectTitle.isVisible = false
                projectCard.isVisible = false
            }
        }
    }

    companion object {
        const val TV_MAX_LINES = 6
    }
}
