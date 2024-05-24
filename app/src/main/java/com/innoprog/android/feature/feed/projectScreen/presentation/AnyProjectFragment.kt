package com.innoprog.android.feature.feed.projectScreen.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentAnyProjectBinding
import com.innoprog.android.di.AppComponentHolder
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.feed.newsfeed.domain.models.Author
import com.innoprog.android.feature.feed.newsfeed.domain.models.Company
import com.innoprog.android.feature.feed.newsfeed.domain.models.News
import com.innoprog.android.feature.feed.projectScreen.di.DaggerAnyProjectComponent
import com.innoprog.android.feature.feed.projectScreen.domain.AnyProjectModel
import com.innoprog.android.feature.newsrecycleview.NewsAdapter
import okhttp3.internal.format

class AnyProjectFragment : BaseFragment<FragmentAnyProjectBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<AnyProjectViewModel>()

    private var newsAdapter: NewsAdapter? = null
    private var listNews: ArrayList<News> = arrayListOf()

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

        viewModel.getAnyProject("1")

        val company = Company(
            "HighTechCorp",
            "CEO"
        )

        val author = Author(
            "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            "https://s3-alpha-sig.figma.com/img/0b35/64f4/7bc6ac8f4998b581668bc2f5a94" +
                "f85bd?Expires=1713139200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=jI3I7" +
                "K7XSeeEULdAe7lPRzZgQsq7QFBYBXEuZK~ZViDvt196iU7N6iH7c9CjBkSouTPDDVi9oWp~ja" +
                "EAcgPmisHin3DlUEIgVGQebnQWL90Ux31RBXODizud2t2Hk~iN2zC-dngHwFwziPYuqsmQ2UH" +
                "LAnUUjetbmeD3N6X12O8~cfOAHc~sArR~8dBFeK8cxaD4SvQWzfttuomT8ydnUL~LtgIFijch" +
                "YW~Qo364qR457Cd5niI7Kgp27Rc515MZmAiIFIvYLqBBNF4cywqk2VtL-nv68MwDduUr6rDXxt" +
                "Vq-a3c6QxvN68lgFZ0LO3V3d05LbV2gv7OwzfSqjPIpg__",
            "Юлия Анисимова",
            company
        )

        val news = News(
            id = "1",
            type = "project",
            author = author,
            projectId = "1",
            coverUrl = "",
            title = "Как мы помогаем родителям в воспитании детей ",
            content = "Этот надежный помощник предназначен для облегчения путей родительства и " +
                "обеспечения гармоничного развития маленьких личностей",
            publishedAt = 24,
            likesCount = 24,
            commentsCount = 24,
        )

        val news2 = News(
            id = "2",
            type = "project",
            author = author,
            projectId = "2",
            coverUrl = "https://img.freepik.com/free-vector/ai-technology-microchip-background-" +
                "vector-digital-transformation-concept_53876-112222.jpg",
            title = "Искусственный интеллект",
            content = "Иску́сственный интелле́кт — свойство искусственных интеллектуальных систем " +
                "выполнять творческие функции, которые традиционно считаются прерогативой " +
                "человека (не следует путать с искусственным сознанием)",
            publishedAt = 24,
            likesCount = 24,
            commentsCount = 24,
        )

        listNews = arrayListOf(news, news2, news, news2)

        initRecyclerView()
    }

    private fun setUiListeners() {
        binding.apply {
            projectTopBar.setLeftIconClickListener {
                viewModel.navigateUp()
            }

            btnProjectDetails.setOnClickListener {
                Toast.makeText(requireContext(), "Открытие деталей проекта", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun initRecyclerView() {
        newsAdapter = NewsAdapter(listNews, object : NewsAdapter.OnClickListener {
            override fun onItemClick(news: News) {
                val action = AnyProjectFragmentDirections.actionProjectFragmentToNewsDetailsFragment(news.id)
                findNavController().navigate(action)
            }
        })

        binding.rvPublications.adapter = newsAdapter
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
        }
    }
}
