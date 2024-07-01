package com.innoprog.android.feature.feed.newsfeed.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.feed.newsfeed.domain.FeedInteractor
import com.innoprog.android.feature.feed.newsfeed.domain.models.Project
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeedViewModel @Inject constructor(private val feedInteractor: FeedInteractor) :
    BaseViewModel() {

    private val _screenState = MutableLiveData<FeedScreenState>()
    val screenState: LiveData<FeedScreenState> = _screenState

    private val _projectDetails = MutableLiveData<Resource<Project>>()
    val projectDetails: LiveData<Resource<Project>> = _projectDetails

    fun getNewsFeed() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                feedInteractor.getNewsFeed().collect { response ->
                    when (response) {
                        is Resource.Success -> {
                            setState(FeedScreenState.Content(response.data))
                        }

                        is Resource.Error -> {
                            setState(FeedScreenState.Error(response.errorType))
                        }
                    }
                }
            }.onFailure { exception ->
                setState(FeedScreenState.Error(ErrorType.BAD_REQUEST))
            }
        }
    }

    fun loadProjectDetails(projectId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val projectDetails = feedInteractor.getProjectDetails(projectId)
            _projectDetails.postValue(projectDetails)
        }
    }


    private fun setState(state: FeedScreenState) {
        _screenState.postValue(state)
    }
}
