package com.innoprog.android.feature.training.courseInformation.domain.useCase

import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformationModel
import com.innoprog.android.feature.training.trainingList.domain.ErrorStatus
import kotlinx.coroutines.flow.Flow

interface GetCourseInformationUseCase {

    fun execute(courseId: String): Flow<Pair<CourseInformationModel?, ErrorStatus?>>
}
