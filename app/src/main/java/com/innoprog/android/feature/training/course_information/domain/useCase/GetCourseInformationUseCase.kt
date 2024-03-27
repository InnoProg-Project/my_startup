package com.innoprog.android.feature.training.course_information.domain.useCase

import com.innoprog.android.feature.training.course_information.presentation.model.CourseInformationModel
import com.innoprog.android.feature.training.trainingList.domain.ErrorStatus
import kotlinx.coroutines.flow.Flow

interface GetCourseInformationUseCase {

    fun execute(courseId: Int): Flow<Pair<CourseInformationModel?, ErrorStatus?>>
}
