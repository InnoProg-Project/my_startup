package com.innoprog.android.feature.training.courseInformation.domain.useCase

import com.innoprog.android.feature.training.courseInformation.domain.CourseInformationRepository
import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformation
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCourseInformationUseCaseImpl @Inject constructor(
    private val repository: CourseInformationRepository
) : GetCourseInformationUseCase {

    override fun execute(courseId: String): Flow<Resource<CourseInformation>> {
        return repository.getCourseInformation(courseId)
    }

}
