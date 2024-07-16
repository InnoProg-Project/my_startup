package com.innoprog.android.feature.feed.newsfeed.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.BuildConfig
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.feed.newsfeed.domain.FeedInteractor
import com.innoprog.android.feature.feed.newsfeed.domain.models.NewsWithProject
import com.innoprog.android.feature.feed.newsfeed.domain.models.QueryPage
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeedViewModel @Inject constructor(private val feedInteractor: FeedInteractor) :
    BaseViewModel() {

    private val _screenState = MutableLiveData<FeedScreenState>()
    val screenState: LiveData<FeedScreenState> = _screenState

    private var jobSearch: DisposableHandle? = null
    private var nextPageNumber = 0
    private var isNextPageLoading = false
    private var lastId = ""
    private var currentListNews = mutableListOf<NewsWithProject>()

    init {
        getNewsFeed()
    }

    fun getNewsFeed(isPagination: Boolean = false) {
        jobSearch?.dispose()
        jobSearch = null
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
                    pageSize = PAGE_SIZE
                )
                feedInteractor.getNewsFeed(queryPage).collect { acceptResponse(isPagination, it) }
            }.onFailure { exception ->
                if (BuildConfig.DEBUG) {
                    Log.v(TAG, "error -> ${exception.localizedMessage}")
                    exception.printStackTrace()
                }
                setState(FeedScreenState.Error(ErrorType.BAD_REQUEST))
            }
        }.invokeOnCompletion {}
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
                setState(FeedScreenState.Error(response.errorType))
            }
        }
    }

    private fun setState(state: FeedScreenState) {
        _screenState.postValue(state)
    }

    fun onLastItemReached() {
        searchPagination()
    }

    private fun searchPagination() {
        if (isNextPageLoading) return

        isNextPageLoading = true
        jobSearch?.dispose()
        jobSearch = null
        getNewsFeed(true)
    }


    fun cancelJobs() {
        jobSearch?.dispose()
        jobSearch = null
        isNextPageLoading = false
    }

    private companion object {
        const val PAGE_SIZE = 20
        val TAG = FeedViewModel::class.simpleName
    }
}
