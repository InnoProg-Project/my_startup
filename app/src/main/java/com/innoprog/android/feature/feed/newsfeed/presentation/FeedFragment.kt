package com.innoprog.android.feature.feed.newsfeed.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import com.innoprog.android.util.ErrorScreenState
import com.innoprog.android.util.debounceFun
import com.innoprog.android.util.debounceUnitFun
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Suppress("Detekt.LargeClass")
class FeedFragment : BaseFragment<FragmentFeedBinding, BaseViewModel>() {

    private val debounceNavigateTo = debounceUnitFun<Fragment?>(lifecycleScope)
    private var editTextIsOnFocus =
        false // переменная необходима для хранение состояния фокуса на edittext

    override val viewModel by injectViewModel<FeedViewModel>()
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter { newsWithProject ->
            publicationTypeIndicator(newsWithProject.news.id, newsWithProject.news.type)
        }
    }
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val debounceGetNewsFeed = debounceFun<String>(
        delayMillis = SEARCH_DELAY_2_SEC,
        coroutineScope = coroutineScope,
        useLastParam = true
    ) { query ->
        viewModel.getNewsFeed(query = query)
    }

    private val textWatcherForEditText = { text: CharSequence?, _: Int, _: Int, _: Int ->
        text?.let {
            debounceGetNewsFeed(text.toString())
        }
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

        binding.rvPublications.adapter = newsAdapter
        binding.rvPublications.layoutManager = LinearLayoutManager(context)
    }

    private fun setUiListeners() = with(binding) {
        btnCreateIdea.setOnClickListener {
            Toast.makeText(requireContext(), "Переход на создание идеи", Toast.LENGTH_SHORT).show()
        }

        etSearch.setOnFocusChangeListener { _, _ ->
            startSearch()
        }

        tvCancel.setOnClickListener { cancelSearch() }

        swipeRefreshLayout.setOnRefreshListener {
            newsAdapter.submitList(emptyList())
            viewModel.getNewsFeed()
        }

        etSearch.doOnTextChanged(textWatcherForEditText)

        rvPublications.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if (!binding.pbNews.isVisible
                        && visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0
                    ) {
                        viewModel.onLastItemReached()
                    }
                }
            }
        })

        layoutErrorScreen.findViewById<com.innoprog.android.uikit.InnoProgButtonView>(
            com.innoprog.android.uikit.R.id.ipbtn_repeat_request
        ).setOnClickListener {
            viewModel.getNewsFeed()
        }
    }

    private fun initChips() {
        val chipTitles = resources.getStringArray(R.array.chips).toList()
        binding.cgvFilter.setChips(chipTitles)
        binding.cgvFilter.setOnChipSelectListener(object :
            InnoProgChipGroupView.OnChipSelectListener {
            override fun onChipSelected(chipIndex: Int) {
                when (chipIndex) {
                    0 -> {
                        viewModel.publicationType = null
                        viewModel.getNewsFeed()
                    }

                    1 -> {
                        viewModel.publicationType = PublicationType.NEWS
                        viewModel.getNewsFeed()
                    }

                    2 -> {
                        viewModel.publicationType = PublicationType.IDEA
                    }
                }
                viewModel.getNewsFeed()
            }
        })
    }

    private fun updateUI(state: FeedScreenState) {
        showPagination(false)
        when (state) {
            is FeedScreenState.Loading -> if (state.isPagination) {
                showPagination(true)
            } else {
                showLoading()
            }

            is FeedScreenState.Content -> showContent(state.newsFeed)
            is FeedScreenState.Error -> renderError(state.errorType)
        }
    }

    fun showPagination(show: Boolean) {
        binding.rvPublications.setPadding(
            binding.rvPublications.paddingLeft,
            binding.rvPublications.paddingTop,
            binding.rvPublications.paddingRight,
            if (show) {
                dpToPx(RV_PADDING_BOTTOM_ON, requireContext())
            } else {
                dpToPx(RV_PADDING_BOTTOM_OFF, requireContext())
            }
        )
        binding.pbNews.isVisible = show
    }

    fun dpToPx(dp: Int, context: Context): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }

    private fun showLoading() = with(binding) {
        swipeRefreshLayout.isRefreshing = false

        listOf(btnCreateIdea, btnCreateIdea, cgvFilter, tvFeed).forEach {
            it.isVisible = editTextIsOnFocus.not()
        }

        listOf(tvPlaceholder, layoutErrorScreen, rvPublications).forEach {
            it.isVisible = false
        }

        listOf(
            cgvFilter,
            circularProgress,
            etSearch
        ).forEach {
            it.isVisible = true
        }
    }

    private fun renderError(errorState: ErrorScreenState) = with(binding) {
        listOf(
            tvPlaceholder,
            cgvFilter,
            etSearch,
            btnCreateIdea,
            tvFeed,
            btnCreateIdea,
            circularProgress
        ).forEach {
            it.isVisible = false
        }
        if (errorState == ErrorScreenState.UNAUTHORIZED) {
            viewModel.clearBackStackAndNavigateToAuthorization()
        } else {
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

    private fun showContent(newsFeed: List<NewsWithProject>) = with(binding) {
        swipeRefreshLayout.isRefreshing = false

        listOf(tvFeed, cgvFilter, btnCreateIdea).forEach {
            it.isVisible = editTextIsOnFocus.not()
        }

        listOf(tvPlaceholder, circularProgress, layoutErrorScreen).forEach {
            it.isVisible = false
        }

        listOf(etSearch, rvPublications).forEach {
            it.isVisible = true
        }
        newsAdapter.submitList(newsFeed)
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
        editTextIsOnFocus = true
        binding.apply {
            btnCreateIdea.isVisible = false
            cgvFilter.isVisible = false
            tvFeed.isVisible = false
            tvCancel.isVisible = true
            changeElementBinding()
        }
    }

    private fun cancelSearch() {
        editTextIsOnFocus = false
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

    override fun onResume() {
        super.onResume()
        newsAdapter.notifyDataSetChanged()
    }

    override fun onPause() {
        super.onPause()
        viewModel.cancelJobs()
    }

    companion object {
        const val RV_PADDING_BOTTOM_ON = 50
        const val RV_PADDING_BOTTOM_OFF = 5
        private const val SEARCH_DELAY_2_SEC = 2000L
    }
}
