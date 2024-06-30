package com.innoprog.android.feature.feed.newsdetails.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.base.SingleLiveEvent
import com.innoprog.android.feature.feed.newsdetails.domain.NewsDetailsInteractor
import com.innoprog.android.util.debounceFun
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsDetailsViewModel @Inject constructor(
    private val newsDetailsInteractor: NewsDetailsInteractor
) :
    BaseViewModel() {

    private val _screenState = MutableLiveData<NewsDetailsScreenState>()
    val screenState: LiveData<NewsDetailsScreenState> = _screenState

    private val onProjectClickDebounce =
        debounceFun<String>(CLICK_DELAY, viewModelScope, false) { projectId ->
            showProject.postValue(projectId)
        }

    private val showProject = SingleLiveEvent<String>()

    fun getShowProjectTrigger(): SingleLiveEvent<String> = showProject

    fun getNewsDetails(newsId: String) {
        viewModelScope.launch {
            val (newsDetails, errorStatus) = newsDetailsInteractor.getNewsDetails(newsId)
            if (errorStatus != null) {
                // Обработка ошибки
                setState(NewsDetailsScreenState.Error)
            } else {
                // Обработка успешного результата
                setState(NewsDetailsScreenState.Content(newsDetails))
            }
        }
    }

    private fun setState(state: NewsDetailsScreenState) {
        _screenState.value = state
    }

    fun openProject() {
        val stateValue = screenState.value
        when (stateValue) {
            is NewsDetailsScreenState.Content -> stateValue.newsDetails?.projectId?.let { onProjectClickDebounce(it) }
            else -> null
        }
    }

    companion object {
        private const val CLICK_DELAY = 300L
    }

}
