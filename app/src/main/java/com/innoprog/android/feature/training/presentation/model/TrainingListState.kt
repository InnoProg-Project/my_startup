package com.innoprog.android.feature.training.presentation.model

sealed interface TrainingListState {
    data object Load : TrainingListState
    data class Content(val trainingList: List<TrainingListModel>) : TrainingListState
    data object Error : TrainingListState
}
