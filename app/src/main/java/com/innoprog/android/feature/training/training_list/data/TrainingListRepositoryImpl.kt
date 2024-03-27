package com.innoprog.android.feature.training.training_list.data

import com.innoprog.android.feature.training.training_list.domain.ErrorStatus
import com.innoprog.android.feature.training.training_list.domain.TrainingListRepository
import com.innoprog.android.feature.training.training_list.presentation.model.TrainingListModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TrainingListRepositoryImpl @Inject constructor() : TrainingListRepository {

    private val trainingListModel: TrainingListModel = TrainingListModel(
        TRAINING_ID,
        TRAINING_DIRECTION,
        TRAINING_TITLE,
        TRAINING_DESCRIPTION,
        TRAINING_AVATAR_URL,
        TRAINING_NAME,
        TRAINING_AUTHOR_POSITION,
        TRAINING_DATE
    )
    private val models = listOf(trainingListModel, trainingListModel, trainingListModel, trainingListModel)

    override fun getTrainingList(): Flow<Pair<List<TrainingListModel>?, ErrorStatus?>> = flow {
        emit(Pair(models, null))
    }

    companion object {

        const val TRAINING_ID = 123
        const val TRAINING_DIRECTION = "Управление"
        const val TRAINING_TITLE = "Интеграция сервисов"
        const val TRAINING_DESCRIPTION =
            "Небольшой курс о том, как интегрировать сервисы в ваше приложение или сайт без участия разработчика"
        const val TRAINING_AVATAR_URL =
            "https://wallpapers4screen.com/Uploads/27-1-2016/18417/cat-tiger-white-cat-cats-photo.jpg"
        const val TRAINING_NAME = "Унтура Михаил"
        const val TRAINING_AUTHOR_POSITION = "СЕО в Мой Стартап"
        const val TRAINING_DATE = "20 мая"
    }
}
