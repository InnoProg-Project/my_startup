package com.innoprog.android.feature.feed.newsfeed.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
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
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentFeedBinding
import com.innoprog.android.di.AppComponentHolder
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.feed.newsfeed.di.DaggerFeedComponent
import com.innoprog.android.feature.feed.newsfeed.domain.models.NewsWithProject
import com.innoprog.android.feature.feed.newsfeed.domain.models.PublicationType
import com.innoprog.android.feature.newsrecycleview.NewsAdapter
import com.innoprog.android.uikit.InnoProgChipGroupView
import com.innoprog.android.util.debounceUnitFun

class FeedFragment : BaseFragment<FragmentFeedBinding, BaseViewModel>() {

    private val debounceNavigateTo = debounceUnitFun<Fragment?>(lifecycleScope)

    override val viewModel by injectViewModel<FeedViewModel>()
    private var listNews: ArrayList<NewsWithProject> = arrayListOf()
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter(listNews) { newsWithProject ->
            publicationTypeIndicator(newsWithProject.news.id, newsWithProject.news.type)
        }
    }
    private val textWatcherForEditText = { text: CharSequence?, _: Int, _: Int, _: Int ->
        changeIconClearVisibility(text)
    }

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

        viewModel.screenState.observe(viewLifecycleOwner) {
            updateUI(it)
        }

        viewModel.getNewsFeed()

        binding.rvPublications.adapter = newsAdapter
    }

    private fun setUiListeners() {
        binding.btnCreateIdea.setOnClickListener {
            Toast.makeText(requireContext(), "Переход на создание идеи", Toast.LENGTH_SHORT).show()
        }

        binding.etSearch.setOnFocusChangeListener { _, _ ->
            startSearch()
        }

        binding.tvCancel.setOnClickListener {
            cancelSearch()
        }

        binding.etSearch.doOnTextChanged(textWatcherForEditText)
    }

    private fun initChips() {
        val chipTitles = resources.getStringArray(R.array.chips).toList()
        binding.cgvFilter.setChips(chipTitles)
        binding.cgvFilter.setOnChipSelectListener(object :
            InnoProgChipGroupView.OnChipSelectListener {
            override fun onChipSelected(chipIndex: Int) {
                // Если нужно обработать чип
            }
        })
    }

    private fun updateUI(state: FeedScreenState) {
        when (state) {
            is FeedScreenState.Loading -> showLoading()
            is FeedScreenState.Content -> showContent(state.newsFeed)
            is FeedScreenState.Error -> showError()
        }
    }

    private fun showLoading() {
        Toast.makeText(requireContext(), "Загрузка", Toast.LENGTH_SHORT).show()
    }

    private fun showError() {
        Toast.makeText(requireContext(), "FeedScreenState Ошибка", Toast.LENGTH_SHORT).show()
    }

    private fun showContent(newsFeed: List<NewsWithProject>) {
        binding.apply {
            rvPublications.isVisible = true
            newsAdapter.newsList.clear()
            newsAdapter.newsList.addAll(newsFeed)
            newsAdapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun changeIconClearVisibility(text: CharSequence?) {
        val editText = binding.etSearch

        if (text.isNullOrEmpty()) {
            editText.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_search,
                0,
                0,
                0
            )
            editText.setOnTouchListener { _, _ ->
                false
            }
        } else {
            editText.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_search,
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
    @Suppress("Detekt.LabeledExpression")
    private fun clearSearchBar() {
        val editText = binding.etSearch
        val iconClear = ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete)
        val iconWidth = iconClear?.intrinsicWidth ?: 0

        editText.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP && event.rawX >=
                editText.right - editText.compoundPaddingEnd - iconWidth
            ) {
                editText.text?.clear()
                editText.requestFocus()
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

    private fun publicationTypeIndicator(newsId: String, newsType: String) {
        if (newsType == PublicationType.NEWS.value) {
            val action = FeedFragmentDirections.actionFeedFragmentToNewsDetailsFragment(newsId)
            debounceNavigateTo(this) { fragment ->
                findNavController().navigate(action)
            }
        } else {
            val action = FeedFragmentDirections.actionFeedFragmentToIdeaDetailsFragment(newsId)
            debounceNavigateTo(this) { fragment ->
                findNavController().navigate(action)
            }
        }
    }
}
