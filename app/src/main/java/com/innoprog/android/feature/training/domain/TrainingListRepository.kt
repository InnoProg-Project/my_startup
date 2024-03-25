package com.innoprog.android.feature.training.domain

import com.innoprog.android.feature.training.presentation.model.TrainingListModel
import kotlinx.coroutines.flow.Flow

interface TrainingListRepository {

    fun getTrainingList(): Flow<Pair<List<TrainingListModel>?, ErrorStatus?>>
}
