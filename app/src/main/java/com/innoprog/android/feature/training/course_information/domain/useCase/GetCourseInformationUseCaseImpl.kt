package com.innoprog.android.feature.training.course_information.domain.useCase

import com.innoprog.android.feature.training.course_information.domain.CourseInformationRepository
import com.innoprog.android.feature.training.course_information.presentation.model.CourseInformationModel
import com.innoprog.android.feature.training.training_list.domain.ErrorStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCourseInformationUseCaseImpl @Inject constructor(private val repository: CourseInformationRepository) : GetCourseInformationUseCase {

    override fun execute(courseId: Int): Flow<Pair<CourseInformationModel?, ErrorStatus?>> {
        return repository.getCourseInformation(courseId)
    }
}
