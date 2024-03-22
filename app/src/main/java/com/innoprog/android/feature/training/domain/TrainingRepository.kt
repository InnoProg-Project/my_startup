package com.innoprog.android.feature.training.domain

import com.innoprog.android.feature.training.presentation.model.TrainingModel
import kotlinx.coroutines.flow.Flow

interface TrainingRepository {

    fun getTrainingList(): Flow<Pair<List<TrainingModel>?, ErrorStatus?>>
}
