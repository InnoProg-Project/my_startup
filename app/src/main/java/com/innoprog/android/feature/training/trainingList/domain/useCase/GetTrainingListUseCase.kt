package com.innoprog.android.feature.training.trainingList.domain.useCase

import com.innoprog.android.feature.training.trainingList.domain.ErrorStatus
import com.innoprog.android.feature.training.trainingList.presentation.model.TrainingListModel
import kotlinx.coroutines.flow.Flow

interface GetTrainingListUseCase {

    fun execute(): Flow<Pair<List<TrainingListModel>?, ErrorStatus?>>
}
