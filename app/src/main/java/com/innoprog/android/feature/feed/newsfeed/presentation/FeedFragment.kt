package com.innoprog.android.feature.feed.newsfeed.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentFeedBinding
import com.innoprog.android.di.AppComponentHolder
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.feed.newsfeed.di.DaggerFeedComponent
import com.innoprog.android.feature.feed.newsfeed.domain.models.Author
import com.innoprog.android.feature.feed.newsfeed.domain.models.Company
import com.innoprog.android.feature.feed.newsfeed.domain.models.News
import com.innoprog.android.feature.newsrecycleview.NewsAdapter
import com.innoprog.android.uikit.InnoProgChipGroupView

class FeedFragment : BaseFragment<FragmentFeedBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<FeedViewModel>()

    private var newsAdapter: NewsAdapter? = null
    private var listNews: ArrayList<News> = arrayListOf()

    override fun diComponent(): ScreenComponent {
        val appComponent = AppComponentHolder.getComponent()
        return DaggerFeedComponent.builder()
            .appComponent(appComponent)
            .build()
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFeedBinding {
        return FragmentFeedBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUiListeners()
        initChips()

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

        binding.tvFeed.setOnClickListener {
            viewModel.onFavoriteClicked(news)
        }
    }

    private fun setUiListeners() {
        binding.btnCreateIdea.setOnClickListener {
            Toast.makeText(requireContext(), "Переход на создание идеи", Toast.LENGTH_SHORT).show()
        }

        binding.etSearch.setOnFocusChangeListener { view, hasFocus ->
            startSearch()
        }

        binding.tvCancel.setOnClickListener {
            cancelSearch()
        }

        binding.etSearch.doOnTextChanged(textWatcherForEditText)
    }

    private fun initChips() {
        val chipTitles = listOf(ALL_CONTENT, PROJECT, IDEAS)
        binding.cgvFilter.setChips(chipTitles)
        binding.cgvFilter.setOnChipSelectListener(object :
            InnoProgChipGroupView.OnChipSelectListener {
            override fun onChipSelected(chipIndex: Int) {
                // Если нужно обработать чип
            }
        })
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

    val textWatcherForEditText = { text: CharSequence?, start: Int, before: Int, count: Int ->
        changeIconClearVisibility(text)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun changeIconClearVisibility(text: CharSequence?) {
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
        }

        clearSearchBar()
    }

    private fun startSearch() {
        binding.apply {
            btnCreateIdea.isVisible = false
            tvFeed.isVisible = false
            cgvFilter.isVisible = false
            tvCancel.isVisible = true
            changeElementBinding()
        }
    }

    private fun cancelSearch() {
        binding.apply {
            etSearch.clearFocus()
            etSearch.text.clear()
            hideKeyboard()
            btnCreateIdea.isVisible = true
            tvFeed.isVisible = true
            cgvFilter.isVisible = true
            tvCancel.isVisible = false
            changeElementBinding()
        }
    }

    private fun changeElementBinding() {
        val constraintLayout = binding.root

        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)

        val marginBetweenTvFeedAndEtSearch =
            resources.getDimensionPixelSize(com.innoprog.android.uikit.R.dimen.margin_16)

        if (binding.btnCreateIdea.isVisible && binding.tvFeed.isVisible) {
            constraintSet.connect(
                R.id.etSearch,
                ConstraintSet.TOP,
                R.id.tvFeed,
                ConstraintSet.BOTTOM,
                marginBetweenTvFeedAndEtSearch
            )
        } else {
            constraintSet.connect(
                R.id.etSearch,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP,
            )
        }

        constraintSet.applyTo(constraintLayout)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun clearSearchBar() {
        val editText = binding.etSearch
        val iconClear = ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete)
        val iconWidth = iconClear?.intrinsicWidth ?: 0

        editText.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP && event.rawX >=
                ((editText.right - editText.compoundPaddingEnd - iconWidth))
            ) {
                editText.text?.clear()
                editText.requestFocus()
                Log.d("Search_click", "Clear button clicked")
                return@setOnTouchListener true
            }
            return@setOnTouchListener false
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
    }

    companion object {
        private const val ALL_CONTENT = "Всё"
        private const val PROJECT = "Проекты"
        private const val IDEAS = "Идеи"
    }
}
