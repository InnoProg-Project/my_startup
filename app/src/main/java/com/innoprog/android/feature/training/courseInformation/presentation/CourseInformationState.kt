package com.innoprog.android.feature.training.courseInformation.presentation

import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformation
import com.innoprog.android.util.ErrorScreenState

sealed interface CourseInformationState {
    data object Load : CourseInformationState
    class Content(val courseInformation: CourseInformation) : CourseInformationState
    class Error(val errorType: ErrorScreenState) : CourseInformationState
}
