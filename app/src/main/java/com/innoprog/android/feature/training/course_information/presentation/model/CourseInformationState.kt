package com.innoprog.android.feature.training.course_information.presentation.model

sealed interface CourseInformationState {
    data object Load : CourseInformationState
    data class Content(val courseInformation: CourseInformationModel) : CourseInformationState
    data object Error : CourseInformationState
}
