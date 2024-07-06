package com.innoprog.android.feature.training.trainingList.domain

import com.innoprog.android.feature.training.trainingList.domain.model.CourseShort
import com.innoprog.android.feature.training.trainingList.domain.model.GetCourseListError
import com.innoprog.android.util.Result
import kotlinx.coroutines.flow.Flow

interface TrainingListRepository {
    fun getTrainingList(): Flow<Result<List<CourseShort>, GetCourseListError>>
}
