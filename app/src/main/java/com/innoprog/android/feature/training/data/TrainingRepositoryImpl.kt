package com.innoprog.android.feature.training.data

import com.innoprog.android.feature.training.domain.ErrorStatus
import com.innoprog.android.feature.training.domain.TrainingRepository
import com.innoprog.android.feature.training.presentation.model.TrainingModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TrainingRepositoryImpl @Inject constructor() : TrainingRepository {

    private val trainingModel: TrainingModel = TrainingModel(
        TRAINING_DIRECTION,
        TRAINING_TITLE,
        TRAINING_DESCRIPTION,
        TRAINING_AVATAR_URL,
        TRAINING_NAME,
        TRAINING_AUTHOR_POSITION,
        TRAINING_DATE
    )
    private val models = listOf(trainingModel, trainingModel, trainingModel, trainingModel)

    override fun getTrainingList(): Flow<Pair<List<TrainingModel>?, ErrorStatus?>> = flow {
        emit(Pair(models, null))
    }

    companion object {

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
