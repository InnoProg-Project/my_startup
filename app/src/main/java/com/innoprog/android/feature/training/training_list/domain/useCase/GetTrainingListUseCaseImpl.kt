package com.innoprog.android.feature.training.training_list.domain.useCase

import com.innoprog.android.feature.training.training_list.domain.ErrorStatus
import com.innoprog.android.feature.training.training_list.domain.TrainingListRepository
import com.innoprog.android.feature.training.training_list.presentation.model.TrainingListModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrainingListUseCaseImpl @Inject constructor(private val repository: TrainingListRepository) :
    GetTrainingListUseCase {

    override fun execute(): Flow<Pair<List<TrainingListModel>?, ErrorStatus?>> {
        return repository.getTrainingList()
    }
}
