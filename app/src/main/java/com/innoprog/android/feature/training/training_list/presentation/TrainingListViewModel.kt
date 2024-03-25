package com.innoprog.android.feature.training.training_list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.training.training_list.domain.useCase.GetTrainingListUseCase
import com.innoprog.android.feature.training.training_list.presentation.model.TrainingListState
import kotlinx.coroutines.launch
import javax.inject.Inject

class TrainingListViewModel @Inject constructor(private val getTrainingListUseCase: GetTrainingListUseCase) :
    BaseViewModel() {

    private val _state = MutableLiveData<TrainingListState>()
    val state: LiveData<TrainingListState> = _state

    init {
        setState(TrainingListState.Load)
        viewModelScope.launch {
            getTrainingListUseCase.execute().collect {
                when {
                    it.first == null -> setState(TrainingListState.Error)
                    it.second == null -> setState(TrainingListState.Content(it.first!!))
                }
            }
        }
    }

    private fun setState(state: TrainingListState) {
        _state.value = state
    }
}
