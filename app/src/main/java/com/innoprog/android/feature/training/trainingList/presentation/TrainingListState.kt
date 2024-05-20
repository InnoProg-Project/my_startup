package com.innoprog.android.feature.training.trainingList.presentation

import com.innoprog.android.feature.training.trainingList.domain.model.CourseShort

sealed interface TrainingListState {
    data object Load : TrainingListState
    class Content(val trainingList: List<CourseShort>) : TrainingListState
    data object Error : TrainingListState
}
