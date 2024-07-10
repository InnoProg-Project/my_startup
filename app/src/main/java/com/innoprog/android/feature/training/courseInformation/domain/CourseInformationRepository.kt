package com.innoprog.android.feature.training.courseInformation.domain

import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformation
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow

interface CourseInformationRepository {

    fun getCourseInformation(courseId: String): Flow<Resource<CourseInformation>>
}
