package com.innoprog.android.feature.feed.newsfeed.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.BuildConfig
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.feed.newsfeed.domain.FeedInteractor
import com.innoprog.android.feature.feed.newsfeed.domain.models.PublicationType
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeedViewModel @Inject constructor(private val feedInteractor: FeedInteractor) :
    BaseViewModel() {

    private val _screenState = MutableLiveData<FeedScreenState>()
    val screenState: LiveData<FeedScreenState> = _screenState

    fun getNewsFeed(type: PublicationType? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                feedInteractor.getNewsFeed(type).collect { response ->
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
                if (BuildConfig.DEBUG) {
                    Log.v(TAG, "exception -> ${exception.localizedMessage}")
                    exception.printStackTrace()
                }
                setState(FeedScreenState.Error(ErrorType.BAD_REQUEST))
            }
        }
    }

    private fun setState(state: FeedScreenState) {
        _screenState.postValue(state)
    }

    companion object {
        private val TAG = FeedViewModel::class.simpleName
    }
}
