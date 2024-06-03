package com.innoprog.android.feature.training.trainingList.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.training.trainingList.domain.useCase.GetTrainingListUseCase
import com.innoprog.android.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TrainingListViewModel @Inject constructor(
    private val getTrainingListUseCase: GetTrainingListUseCase
) : BaseViewModel() {
    private val _state = MutableLiveData<TrainingListState>()
    val state: LiveData<TrainingListState> = _state

    init {
        setState(TrainingListState.Load)
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                getTrainingListUseCase.execute().collect { result ->
                    withContext(Dispatchers.Main) {
                        when (result) {
                            is Result.Error -> setState(TrainingListState.Error)
                            is Result.Success -> setState(TrainingListState.Content(result.data))
                        }
                    }
                }
            }.onFailure { exception ->
                withContext(Dispatchers.Main) {
                    setState(TrainingListState.Error)
                    Log.e(TAG, "error -> ${exception.localizedMessage}")
                    exception.printStackTrace()
                }
            }
        }
    }

    private fun setState(state: TrainingListState) {
        _state.value = state
    }

    companion object {
        private val TAG = TrainingListViewModel::class.simpleName
    }
}
