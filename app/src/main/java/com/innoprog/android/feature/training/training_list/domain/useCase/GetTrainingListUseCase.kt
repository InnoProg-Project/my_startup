package com.innoprog.android.feature.training.training_list.domain.useCase

import com.innoprog.android.feature.training.training_list.domain.ErrorStatus
import com.innoprog.android.feature.training.training_list.presentation.model.TrainingListModel
import kotlinx.coroutines.flow.Flow

interface GetTrainingListUseCase {

    fun execute(): Flow<Pair<List<TrainingListModel>?, ErrorStatus?>>
}
