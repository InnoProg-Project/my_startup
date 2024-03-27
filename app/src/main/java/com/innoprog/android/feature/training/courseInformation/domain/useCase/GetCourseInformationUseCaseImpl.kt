package com.innoprog.android.feature.training.courseInformation.domain.useCase

import com.innoprog.android.feature.training.courseInformation.domain.CourseInformationRepository
import com.innoprog.android.feature.training.courseInformation.presentation.model.CourseInformationModel
import com.innoprog.android.feature.training.trainingList.domain.ErrorStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCourseInformationUseCaseImpl @Inject constructor(
    private val repository: CourseInformationRepository
) : GetCourseInformationUseCase {

    override fun execute(courseId: Int): Flow<Pair<CourseInformationModel?, ErrorStatus?>> {
        return repository.getCourseInformation(courseId)
    }
}
