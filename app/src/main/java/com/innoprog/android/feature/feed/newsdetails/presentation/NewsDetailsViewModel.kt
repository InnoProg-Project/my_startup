package com.innoprog.android.feature.feed.newsdetails.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.BuildConfig
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.base.SingleLiveEvent
import com.innoprog.android.feature.feed.newsdetails.domain.NewsDetailsInteractor
import com.innoprog.android.feature.feed.newsdetails.domain.models.CommentModel
import com.innoprog.android.feature.feed.newsdetails.domain.models.NewsDetailsModel
import com.innoprog.android.util.ErrorScreenState
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import com.innoprog.android.util.debounceFun
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsDetailsViewModel @Inject constructor(
    private val newsDetailsInteractor: NewsDetailsInteractor
) : BaseViewModel() {
    private val _screenState =
        MutableLiveData<NewsDetailsScreenState>(NewsDetailsScreenState.Loading)
    val screenState: LiveData<NewsDetailsScreenState> = _screenState

    private val _addCommentResult = MutableLiveData<Resource<CommentModel>>()
    val addCommentResult: LiveData<Resource<CommentModel>> get() = _addCommentResult

    private val onProjectClickDebounce =
        debounceFun<String>(CLICK_DELAY, viewModelScope, false) { projectId ->
            showProject.postValue(projectId)
        }

    private val showProject = SingleLiveEvent<String>()

    fun getShowProjectTrigger(): SingleLiveEvent<String> = showProject


    fun addComment(publicationId: String, content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                _addCommentResult.postValue(
                    newsDetailsInteractor.addComment(publicationId, content)
                )
            }.onFailure { error ->
                if (BuildConfig.DEBUG) {
                    Log.e(TAG, "error -> ${error.localizedMessage}")
                    error.printStackTrace()
                }
            }
        }
    }

    fun getNewsDetails(newsId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val newsDetailsResult = newsDetailsInteractor.getNewsDetails(newsId)
                when (newsDetailsResult) {
                    is Resource.Success -> {
                        val newsDetails = newsDetailsResult.data
                        getComments(newsId, newsDetails)
                    }

                    is Resource.Error -> {
                        setState(renderError(newsDetailsResult.errorType))
                    }
                }
            }.onFailure { error ->
                if (BuildConfig.DEBUG) {
                    Log.v(TAG, "error -> ${error.localizedMessage}")
                    error.printStackTrace()
                }
                setState(NewsDetailsScreenState.Error(ErrorScreenState.SERVER_ERROR))
            }
        }
    }

    private fun getComments(newsId: String, newsDetails: NewsDetailsModel) {
        viewModelScope.launch {
            newsDetailsInteractor.getComments(newsId)
                .catch { exception ->
                    if (BuildConfig.DEBUG) {
                        Log.e(TAG, "error -> ${exception.localizedMessage}")
                        exception.printStackTrace()
                    }
                    setState(NewsDetailsScreenState.Error(ErrorScreenState.SERVER_ERROR))
                }
                .collect { commentsResult ->
                    when (commentsResult) {
                        is Resource.Success -> {
                            val comments = commentsResult.data
                            setState(NewsDetailsScreenState.Content(newsDetails, comments))
                        }

                        is Resource.Error -> {
                            setState(renderError(commentsResult.errorType))
                        }
                    }
                }
        }
    }

    private fun renderError(errorType: ErrorType): NewsDetailsScreenState.Error {
        return when (errorType) {
            ErrorType.NO_CONNECTION -> NewsDetailsScreenState.Error(ErrorScreenState.NO_INTERNET)
            ErrorType.NOT_FOUND -> NewsDetailsScreenState.Error(ErrorScreenState.NOT_FOUND)
            ErrorType.INTERNAL_SERVER_ERROR -> NewsDetailsScreenState.Error(ErrorScreenState.SERVER_ERROR)
            ErrorType.UNAUTHORIZED -> NewsDetailsScreenState.Error(ErrorScreenState.UNAUTHORIZED)
            else -> NewsDetailsScreenState.Error(ErrorScreenState.SERVER_ERROR)
        }
    }

    private fun setState(state: NewsDetailsScreenState) {
        _screenState.postValue(state)
    }

    fun openProject() {
        when (val stateValue = screenState.value) {
            is NewsDetailsScreenState.Content -> {
                stateValue.newsDetails.projectId?.let { onProjectClickDebounce(it) }
            }
            else -> {}
        }
    }

    companion object {
        private const val CLICK_DELAY = 300L
        private val TAG = NewsDetailsViewModel::class.simpleName
    }
}
