package com.innoprog.android.feature.feed.anyProjectDetails.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.feed.anyProjectDetails.domain.AnyProjectDetailsInteractor
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AnyProjectDetailsViewModel @Inject constructor(
    private val anyProjectDetailsInteractor: AnyProjectDetailsInteractor
) :
    BaseViewModel() {

    private val _screenState = MutableLiveData<AnyProjectDetailsScreenState>()
    val screenState: LiveData<AnyProjectDetailsScreenState> = _screenState

    fun getAnyProjectDetails(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                anyProjectDetailsInteractor.getAnyProjectDetails(id).collect { response ->
                    when (response) {
                        is Resource.Success -> {
                            setState(AnyProjectDetailsScreenState.Content(response.data))
                        }

                        is Resource.Error -> {
                            setState(AnyProjectDetailsScreenState.Error(response.errorType))
                        }
                    }
                }
            }.onFailure { exception ->
                setState(AnyProjectDetailsScreenState.Error(ErrorType.BAD_REQUEST))
            }
        }
    }

    private fun setState(state: AnyProjectDetailsScreenState) {
        _screenState.postValue(state)
    }
}
