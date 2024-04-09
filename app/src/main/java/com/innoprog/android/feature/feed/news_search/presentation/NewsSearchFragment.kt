package com.innoprog.android.feature.feed.news_search.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentNewsSearchBinding
import com.innoprog.android.di.AppComponentHolder
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.feed.news_feed.domain.models.Author
import com.innoprog.android.feature.feed.news_feed.domain.models.Company
import com.innoprog.android.feature.feed.news_feed.domain.models.News
import com.innoprog.android.feature.feed.news_search.di.DaggerNewsSearchComponent
import com.innoprog.android.feature.news_recycle_view.NewsAdapter

class NewsSearchFragment : BaseFragment<FragmentNewsSearchBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<NewsSearchViewModel>()

    private var newsAdapter: NewsAdapter? = null
    private var listNews: ArrayList<News> = arrayListOf()

    override fun diComponent(): ScreenComponent {
        val appComponent = AppComponentHolder.getComponent()
        return DaggerNewsSearchComponent.builder()
            .appComponent(appComponent)
            .build()
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNewsSearchBinding {
        return FragmentNewsSearchBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val company = Company(
            "HighTechCorp",
            "CEO"
        )

        val author = Author(
            "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            //"",
            "https://s3-alpha-sig.figma.com/img/0b35/64f4/7bc6ac8f4998b581668bc2f5a94f85bd?Expires=1713139200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=jI3I7K7XSeeEULdAe7lPRzZgQsq7QFBYBXEuZK~ZViDvt196iU7N6iH7c9CjBkSouTPDDVi9oWp~jaEAcgPmisHin3DlUEIgVGQebnQWL90Ux31RBXODizud2t2Hk~iN2zC-dngHwFwziPYuqsmQ2UHLAnUUjetbmeD3N6X12O8~cfOAHc~sArR~8dBFeK8cxaD4SvQWzfttuomT8ydnUL~LtgIFijchYW~Qo364qR457Cd5niI7Kgp27Rc515MZmAiIFIvYLqBBNF4cywqk2VtL-nv68MwDduUr6rDXxtVq-a3c6QxvN68lgFZ0LO3V3d05LbV2gv7OwzfSqjPIpg__",
            "Юлия Анисимова",
            company
        )

        val news = News(
            id = "1",
            type = "idea",
            author = author,
            projectId = "123",
            coverUrl = "",
            title = "qwetyuytyu",
            content = "asdnnm,nm,nk,kn,",
            publishedAt = 24,
            likesCount = 24,
            commentsCount = 24,
        )

        val news2 = News(
            id = "2",
            type = "project",
            author = author,
            projectId = "123",
            coverUrl = "https://apod.nasa.gov/apod/image/2110/LucyLaunchB_Kraus_2048.jpg",
            title = "qweweghfhffgf",
            content = "asadafeghjyjjghnfhnhjnhmd",
            publishedAt = 24,
            likesCount = 24,
            commentsCount = 24,
        )

        listNews = arrayListOf(news, news2, news, news2)

        initRecyclerView()
    }


    private fun initRecyclerView() {
        newsAdapter = NewsAdapter(listNews)
        binding.rvPublications.adapter = newsAdapter
    }
}
