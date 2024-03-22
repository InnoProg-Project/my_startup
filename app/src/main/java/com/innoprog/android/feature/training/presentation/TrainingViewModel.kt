package com.innoprog.android.feature.training.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.training.domain.useCase.GetTrainingListUseCase
import com.innoprog.android.feature.training.presentation.model.TrainingState
import kotlinx.coroutines.launch
import javax.inject.Inject

class TrainingViewModel @Inject constructor(private val getTrainingListUseCase: GetTrainingListUseCase)
    : BaseViewModel() {

    private val _state = MutableLiveData<TrainingState>()
    val state: LiveData<TrainingState> = _state

    init {
        setState(TrainingState.Load)
        viewModelScope.launch {
            getTrainingListUseCase.execute().collect {
                when {
                    it.first == null -> setState(TrainingState.Error)
                    it.second == null -> setState(TrainingState.Content(it.first!!))
                }
            }
        }
    }

    private fun setState(state: TrainingState) {
        _state.value = state
    }
}
