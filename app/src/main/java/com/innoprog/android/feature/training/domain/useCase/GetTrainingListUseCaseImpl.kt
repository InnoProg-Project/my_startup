package com.innoprog.android.feature.training.domain.useCase

import com.innoprog.android.feature.training.domain.ErrorStatus
import com.innoprog.android.feature.training.domain.TrainingListRepository
import com.innoprog.android.feature.training.presentation.model.TrainingListModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrainingListUseCaseImpl @Inject constructor(private val repository: TrainingListRepository) :
    GetTrainingListUseCase {

    override fun execute(): Flow<Pair<List<TrainingListModel>?, ErrorStatus?>> {
        return repository.getTrainingList()
    }
}
