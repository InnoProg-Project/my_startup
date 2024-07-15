package com.innoprog.android.feature.feed.projectScreen.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.BuildConfig
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.feed.projectScreen.domain.AnyProjectInteractor
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AnyProjectViewModel @Inject constructor(private val anyProjectInteractor: AnyProjectInteractor) :
    BaseViewModel() {
    private val _screenState = MutableLiveData<AnyProjectScreenState>()
    val screenState: LiveData<AnyProjectScreenState> = _screenState

    fun getAnyProject(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                anyProjectInteractor.getAnyProject(id).collect { response ->
                    when (response) {
                        is Resource.Success -> {
                            setState(AnyProjectScreenState.Content(response.data))
                        }

                        is Resource.Error -> {
                            setState(AnyProjectScreenState.Error(response.errorType))
                        }
                    }
                }
            }.onFailure { exception ->
                if (BuildConfig.DEBUG) {
                    Log.e(TAG, "error -> ${exception.localizedMessage}")
                    exception.printStackTrace()
                }
                setState(AnyProjectScreenState.Error(ErrorType.BAD_REQUEST))
            }
        }
    }

    private fun setState(state: AnyProjectScreenState) {
        _screenState.postValue(state)
    }

    private companion object {
        val TAG = AnyProjectViewModel::class.simpleName
    }
}
