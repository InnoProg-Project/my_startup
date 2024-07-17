package com.innoprog.android.feature.feed.newsfeed.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.BuildConfig
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.feed.newsfeed.domain.FeedInteractor
import com.innoprog.android.feature.feed.newsfeed.domain.models.NewsWithProject
import com.innoprog.android.feature.feed.newsfeed.domain.models.PublicationType
import com.innoprog.android.feature.feed.newsfeed.domain.models.QueryPage
import com.innoprog.android.util.ErrorScreenState
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeedViewModel @Inject constructor(private val feedInteractor: FeedInteractor) :
    BaseViewModel() {

    private val _screenState = MutableLiveData<FeedScreenState>()
    val screenState: LiveData<FeedScreenState>
        get() = _screenState

    private var jobSearch: Job? = null
    private var nextPageNumber = 0
    private var isNextPageLoading = false
    private var lastId = ""
    private var currentListNews = mutableListOf<NewsWithProject>()
    var publicationType: PublicationType? = null

    init {
        getNewsFeed()
    }

    fun getNewsFeed(isPagination: Boolean = false, query: String? = null) {
        jobSearch?.cancel()
        jobSearch = viewModelScope.launch(Dispatchers.IO) {
            setState(FeedScreenState.Loading(isPagination))
            if (!isPagination) {
                nextPageNumber = 0
                lastId = ""
            }
            runCatching {
                nextPageNumber++
                val queryPage = QueryPage(
                    nextPageNumber = nextPageNumber,
                    lastId = lastId,
                    pageSize = PAGE_SIZE,
                    type = publicationType?.value,
                    query = query
                )
                feedInteractor.getNewsFeed(queryPage).collect { acceptResponse(isPagination, it) }
            }.onFailure { exception ->
                if (BuildConfig.DEBUG) {
                    Log.v(TAG, "error -> ${exception.localizedMessage}")
                    exception.printStackTrace()
                }
                setState(FeedScreenState.Error(ErrorScreenState.NOT_FOUND))
            }
        }
    }

    private fun acceptResponse(isPagination: Boolean, response: Resource<List<NewsWithProject>>) {
        when (response) {
            is Resource.Success -> {
                response.data.lastOrNull()?.let { lastId = it.news.id }
                if (isPagination.not()) {
                    currentListNews.clear()
                }
                response.data.forEach { currentListNews.add(it) }

                setState(FeedScreenState.Content(currentListNews))
                isNextPageLoading = false
            }

            is Resource.Error -> {
                handleError(response.errorType)
            }
        }
    }

    private fun setState(state: FeedScreenState) {
        _screenState.postValue(state)
    }

    fun onLastItemReached(query: String?) {
        searchPagination(query)
    }

    private fun searchPagination(query: String?) {
        if (isNextPageLoading) return

        isNextPageLoading = true
        jobSearch?.cancel()
        jobSearch = null
        getNewsFeed(true, query)
    }


    fun cancelJobs() {
        jobSearch?.cancel()
        jobSearch = null
        isNextPageLoading = false
    }

    private fun handleError(errorType: ErrorType) {
        setState(renderError(errorType))
    }


    private fun renderError(errorType: ErrorType): FeedScreenState.Error {
        return when (errorType) {
            ErrorType.NO_CONNECTION -> FeedScreenState.Error(ErrorScreenState.NO_INTERNET)
            ErrorType.NOT_FOUND -> FeedScreenState.Error(ErrorScreenState.NOT_FOUND)
            ErrorType.INTERNAL_SERVER_ERROR -> FeedScreenState.Error(ErrorScreenState.SERVER_ERROR)
            ErrorType.UNAUTHORIZED -> FeedScreenState.Error(ErrorScreenState.UNAUTHORIZED)
            else -> FeedScreenState.Error(ErrorScreenState.SERVER_ERROR)
        }
    }

    companion object {
        const val PAGE_SIZE = 20
        private val TAG = FeedViewModel::class.java.simpleName
    }
}
