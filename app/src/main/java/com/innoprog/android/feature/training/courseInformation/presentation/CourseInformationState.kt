package com.innoprog.android.feature.training.courseInformation.presentation

import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformation
import com.innoprog.android.util.ErrorType

sealed interface CourseInformationState {
    data object Load : CourseInformationState
    data class Content(val courseInformation: CourseInformation) : CourseInformationState
    data class Error(val type: ErrorType) : CourseInformationState
}
