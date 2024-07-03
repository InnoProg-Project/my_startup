package com.innoprog.android.feature.training.courseInformation.domain

import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformationModel
import com.innoprog.android.feature.training.trainingList.domain.ErrorStatus
import kotlinx.coroutines.flow.Flow

interface CourseInformationRepository {

    fun getCourseInformation(courseId: String): Flow<Pair<CourseInformationModel?, ErrorStatus?>>
}
