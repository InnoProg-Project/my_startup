package com.innoprog.android.feature.training.trainingList.domain.useCase

import com.innoprog.android.feature.training.trainingList.domain.ErrorStatus
import com.innoprog.android.feature.training.trainingList.domain.TrainingListRepository
import com.innoprog.android.feature.training.trainingList.presentation.model.TrainingListModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrainingListUseCaseImpl @Inject constructor(private val repository: TrainingListRepository) :
    GetTrainingListUseCase {

    override fun execute(): Flow<Pair<List<TrainingListModel>?, ErrorStatus?>> {
        return repository.getTrainingList()
    }
}
