package com.innoprog.android.feature.training.trainingList.presentation

import com.innoprog.android.feature.training.trainingList.presentation.model.CoursesItem

sealed interface TrainingListState {
    data object Load : TrainingListState
    class Content(val trainingList: List<CoursesItem>) : TrainingListState
    data object Error : TrainingListState
    data object UnAuthorisedError : TrainingListState
    data object EmptyList : TrainingListState
}
