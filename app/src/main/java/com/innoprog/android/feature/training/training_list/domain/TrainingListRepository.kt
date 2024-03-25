package com.innoprog.android.feature.training.training_list.domain

import com.innoprog.android.feature.training.training_list.presentation.model.TrainingListModel
import kotlinx.coroutines.flow.Flow

interface TrainingListRepository {

    fun getTrainingList(): Flow<Pair<List<TrainingListModel>?, ErrorStatus?>>
}
