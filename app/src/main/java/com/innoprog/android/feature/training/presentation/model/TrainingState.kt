package com.innoprog.android.feature.training.presentation.model

sealed interface TrainingState {
    data object Load : TrainingState
    data class Content(val trainingList: List<TrainingModel>) : TrainingState
    data object Error : TrainingState
}
