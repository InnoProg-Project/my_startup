package com.innoprog.android.feature.training.trainingList.domain.useCase

import com.innoprog.android.feature.training.trainingList.domain.TrainingListRepository
import com.innoprog.android.feature.training.trainingList.domain.model.CourseShort
import com.innoprog.android.feature.training.trainingList.domain.model.GetCourseListError
import com.innoprog.android.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrainingListUseCase @Inject constructor(
    private val repository: TrainingListRepository
) {
    fun execute(): Flow<Result<List<CourseShort>, GetCourseListError>> {
        return repository.getTrainingList()
    }
}
