package com.innoprog.android.feature.training.domain.useCase

import com.innoprog.android.feature.training.domain.ErrorStatus
import com.innoprog.android.feature.training.domain.TrainingRepository
import com.innoprog.android.feature.training.presentation.model.TrainingModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrainingListUseCaseImpl @Inject constructor(private val repository: TrainingRepository) :
    GetTrainingListUseCase {

    override fun execute(): Flow<Pair<List<TrainingModel>?, ErrorStatus?>> {
        return repository.getTrainingList()
    }
}
