package com.innoprog.android.feature.training.courseInformation.domain.useCase

import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformation
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow

interface GetCourseInformationUseCase {

    fun execute(courseId: String): Flow<Resource<CourseInformation?>>
}
