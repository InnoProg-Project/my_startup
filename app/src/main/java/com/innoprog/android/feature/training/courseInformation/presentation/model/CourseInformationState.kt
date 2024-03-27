package com.innoprog.android.feature.training.courseInformation.presentation.model

sealed interface CourseInformationState {
    data object Load : CourseInformationState
    data class Content(val courseInformation: CourseInformationModel) : CourseInformationState
    data object Error : CourseInformationState
}
