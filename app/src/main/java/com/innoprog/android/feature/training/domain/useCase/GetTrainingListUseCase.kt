package com.innoprog.android.feature.training.domain.useCase

import com.innoprog.android.feature.training.domain.ErrorStatus
import com.innoprog.android.feature.training.presentation.model.TrainingListModel
import kotlinx.coroutines.flow.Flow

interface GetTrainingListUseCase {

    fun execute(): Flow<Pair<List<TrainingListModel>?, ErrorStatus?>>
}
