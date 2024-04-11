package com.innoprog.android.feature.feed.newssearch.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentNewsSearchBinding
import com.innoprog.android.di.AppComponentHolder
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.feed.newsfeed.domain.models.Author
import com.innoprog.android.feature.feed.newsfeed.domain.models.Company
import com.innoprog.android.feature.feed.newsfeed.domain.models.News
import com.innoprog.android.feature.feed.newssearch.di.DaggerNewsSearchComponent
import com.innoprog.android.feature.newsrecycleview.NewsAdapter

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
            type = "idea",
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
        setUiListeners()
    }

    private fun initRecyclerView() {
        newsAdapter = NewsAdapter(listNews, object : NewsAdapter.OnClickListener {
            override fun onItemClick(news: News) {
                Toast.makeText(requireContext(), "Открытие деталей публикации", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        binding.rvPublications.adapter = newsAdapter
    }

    private fun setUiListeners() {
        binding.tvCancel.setOnClickListener {
            viewModel.navigateUp()
        }

        binding.etSearch.doOnTextChanged(textWatcherForEditText)
    }

    val textWatcherForEditText = { text: CharSequence?, start: Int, before: Int, count: Int ->
        hideIconClear(text)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun hideIconClear(text: CharSequence?) {
        val editText = binding.etSearch

        if (text.isNullOrEmpty()) {
            editText.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_search_light,
                0,
                0,
                0
            )
            editText.setOnTouchListener { _, motionEvent ->
                false
            }
        } else {
            editText.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_search_light,
                0,
                R.drawable.ic_delete,
                0
            )

            val iconClear = editText.compoundDrawables[2]

            editText.setOnTouchListener { _, motionEvent ->
                if ((motionEvent.action == MotionEvent.ACTION_UP) &&
                    (motionEvent.rawX >= (editText.right - iconClear.bounds.width()))
                ) {
                    editText.text.clear()
                    editText.requestFocus()
                    return@setOnTouchListener true
                }
                false
            }
        }
    }
}
