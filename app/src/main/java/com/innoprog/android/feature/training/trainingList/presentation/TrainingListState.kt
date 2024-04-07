package com.innoprog.android.feature.training.trainingList.presentation

import com.innoprog.android.feature.training.trainingList.domain.model.TrainingListModel

sealed interface TrainingListState {
    data object Load : TrainingListState
    data class Content(val trainingList: List<TrainingListModel>) : TrainingListState
    data object Error : TrainingListState
}
