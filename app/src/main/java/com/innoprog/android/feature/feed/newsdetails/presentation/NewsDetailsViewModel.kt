package com.innoprog.android.feature.feed.newsdetails.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.feed.newsdetails.domain.NewsDetailsInteractor
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsDetailsViewModel @Inject constructor(
    private val newsDetailsInteractor: NewsDetailsInteractor
) :
    BaseViewModel() {

    private val _screenState = MutableLiveData<NewsDetailsScreenState>()
    val screenState: LiveData<NewsDetailsScreenState> = _screenState

    fun getNewsDetails(newsId: String) {
        viewModelScope.launch {
            newsDetailsInteractor.getNewsDetails(newsId).collect {
                if (it.first != null) {
                    setState(NewsDetailsScreenState.Content(it.first!!))
                } else {
                    setState(NewsDetailsScreenState.Error)
                }
            }
        }
    }

    private fun setState(state: NewsDetailsScreenState) {
        _screenState.value = state
    }
}
