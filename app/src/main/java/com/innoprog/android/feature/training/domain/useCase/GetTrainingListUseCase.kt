package com.innoprog.android.feature.training.domain.useCase

import com.innoprog.android.feature.training.domain.ErrorStatus
import com.innoprog.android.feature.training.presentation.model.TrainingModel
import kotlinx.coroutines.flow.Flow

interface GetTrainingListUseCase {

    fun execute(): Flow<Pair<List<TrainingModel>?, ErrorStatus?>>
}
