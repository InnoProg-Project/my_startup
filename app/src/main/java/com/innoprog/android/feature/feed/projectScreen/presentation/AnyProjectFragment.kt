package com.innoprog.android.feature.feed.projectScreen.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentAnyProjectBinding
import com.innoprog.android.di.AppComponentHolder
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.feed.newsfeed.domain.models.NewsWithProject
import com.innoprog.android.feature.feed.projectScreen.di.DaggerAnyProjectComponent
import com.innoprog.android.feature.feed.projectScreen.domain.AnyProjectModel
import com.innoprog.android.feature.newsrecycleview.NewsAdapter
import okhttp3.internal.format

class AnyProjectFragment : BaseFragment<FragmentAnyProjectBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<AnyProjectViewModel>()

    private var listNews = ArrayList<NewsWithProject>()

    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter(listNews) { newsWithProject ->
            val action = AnyProjectFragmentDirections.actionProjectFragmentToNewsDetailsFragment(
                newsWithProject.news.id
            )
            findNavController().navigate(action)
        }
    }

    override fun diComponent(): ScreenComponent {
        val appComponent = AppComponentHolder.getComponent()
        return DaggerAnyProjectComponent.builder()
            .appComponent(appComponent)
            .build()
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAnyProjectBinding {
        return FragmentAnyProjectBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUiListeners()

        viewModel.screenState.observe(viewLifecycleOwner) {
            updateUI(it)
        }

        val args: AnyProjectFragmentArgs by navArgs()
        val projectId = args.projectId

        viewModel.getAnyProject(args.projectId, false)

        binding.rvPublications.adapter = newsAdapter
    }

    private fun setUiListeners() {
        binding.apply {
            projectTopBar.setLeftIconClickListener {
                viewModel.navigateUp()
            }

            btnProjectDetails.setOnClickListener {
                val action =
                    AnyProjectFragmentDirections.actionProjectFragmentToAnyProjectDetailsFragment(
                        id.toString()
                    )
                findNavController().navigate(action)
            }
        }
    }

    private fun updateUI(state: AnyProjectScreenState) {
        when (state) {
            is AnyProjectScreenState.Loading -> showLoading()
            is AnyProjectScreenState.Content -> showContent(state.anyProject)
            is AnyProjectScreenState.Error -> showError()
        }
    }

    private fun showLoading() {
        Toast.makeText(requireContext(), "Загрузка", Toast.LENGTH_SHORT).show()
    }

    private fun showError() {
        Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
    }

    private fun showContent(anyProject: AnyProjectModel) {
        val radius = binding.root.resources.getDimensionPixelSize(R.dimen.corner_radius_10)
        binding.apply {
            Glide.with(requireContext())
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
            innoProgButtonView.isVisible = anyProject.itsCustomProject

            if (anyProject.projectNews != null) {
                rvPublications.isVisible = true
                newsAdapter.newsList.clear()
                newsAdapter.newsList.addAll(anyProject.projectNews)
                newsAdapter.notifyDataSetChanged()
            } else {
                rvPublications.isVisible = false
            }
        }
    }
}
