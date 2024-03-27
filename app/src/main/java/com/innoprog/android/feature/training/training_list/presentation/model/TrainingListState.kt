package com.innoprog.android.feature.training.training_list.presentation.model

sealed interface TrainingListState {
    data object Load : TrainingListState
    data class Content(val trainingList: List<TrainingListModel>) : TrainingListState
    data object Error : TrainingListState
}