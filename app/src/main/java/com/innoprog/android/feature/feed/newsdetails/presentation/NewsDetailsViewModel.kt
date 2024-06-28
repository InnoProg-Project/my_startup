package com.innoprog.android.feature.feed.newsdetails.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.feed.anyProjectDetails.presentation.AnyProjectDetailsScreenState
import com.innoprog.android.feature.feed.newsdetails.domain.NewsDetailsInteractor
import com.innoprog.android.feature.feed.newsdetails.domain.models.NewsDetailsModel
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsDetailsViewModel @Inject constructor(
    private val newsDetailsInteractor: NewsDetailsInteractor
) :
    BaseViewModel() {

    private val _screenState = MutableLiveData<NewsDetailsScreenState>()
    val screenState: LiveData<NewsDetailsScreenState> = _screenState

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
                        setState(NewsDetailsScreenState.Error(newsDetailsResult.errorType))
                    }
                }
            }.onFailure { exception ->
                setState(NewsDetailsScreenState.Error(ErrorType.UNEXPECTED))
            }

        }
    }

    private fun getComments(newsId: String, newsDetails: NewsDetailsModel) {
        viewModelScope.launch {
            newsDetailsInteractor.getComments(newsId)
                .catch { exception ->
                    setState(NewsDetailsScreenState.Error(ErrorType.UNEXPECTED))
                }
                .collect { commentsResult ->
                    when (commentsResult) {
                        is Resource.Success -> {
                            val comments = commentsResult.data
                            setState(NewsDetailsScreenState.Content(newsDetails, comments))
                        }
                        is Resource.Error -> {
                            setState(NewsDetailsScreenState.Error(commentsResult.errorType))
                        }
                    }
                }
        }
    }


    private fun setState(state: NewsDetailsScreenState) {
        _screenState.postValue(state)
    }
}
