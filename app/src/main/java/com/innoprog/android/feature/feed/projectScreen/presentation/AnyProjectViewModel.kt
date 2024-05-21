package com.innoprog.android.feature.feed.projectScreen.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.feed.projectScreen.domain.AnyProjectInteractor
import com.innoprog.android.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class AnyProjectViewModel @Inject constructor(private val anyProjectInteractor: AnyProjectInteractor) :
    BaseViewModel() {

    private val _screenState = MutableLiveData<AnyProjectScreenState>()
    val screenState: LiveData<AnyProjectScreenState> = _screenState

    fun getAnyProject(id: String) {
        viewModelScope.launch {
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
        }
    }

    private fun setState(state: AnyProjectScreenState) {
        _screenState.value = state
    }
}
