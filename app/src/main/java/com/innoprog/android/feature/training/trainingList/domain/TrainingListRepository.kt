package com.innoprog.android.feature.training.trainingList.domain

import com.innoprog.android.feature.training.trainingList.domain.model.TrainingListModel
import kotlinx.coroutines.flow.Flow

interface TrainingListRepository {

    fun getTrainingList(): Flow<Pair<List<TrainingListModel>?, ErrorStatus?>>
}
