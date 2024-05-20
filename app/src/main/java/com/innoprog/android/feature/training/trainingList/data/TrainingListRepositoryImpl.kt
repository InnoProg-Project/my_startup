package com.innoprog.android.feature.training.trainingList.data

import com.innoprog.android.feature.training.trainingList.domain.TrainingListRepository
import com.innoprog.android.feature.training.trainingList.domain.model.CourseShort
import com.innoprog.android.feature.training.trainingList.domain.model.GetCourseListError
import com.innoprog.android.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TrainingListRepositoryImpl @Inject constructor() : TrainingListRepository {
    override fun getTrainingList(): Flow<Result<List<CourseShort>, GetCourseListError>> = flow {
        emit(Result.Success(data = mockData()))
    }

    private fun mockData(): List<CourseShort> {
        val mockList = mutableListOf<CourseShort>().apply {
            repeat(MOCK_LIST_SIZE) { position ->
                add(
                    CourseShort(
                        id = position.toString(),
                        direction = "Управление",
                        title = "Интеграция сервисов - $position",
                        avatarURL = MOCK_AVATAR_URL,
                        description = MOCK_DESCRIPTION,
                        authorName = "Унтура Михаил",
                        authorPost = "СЕО в Мой Стартап",
                        createdDate = "20 мая"
                    )
                )
            }
        }
        return mockList
    }

    companion object {
        private const val MOCK_AVATAR_URL = "https://wallpapers4screen.com/Uploads/27-1-2016/18417/cat-tiger-white-cat-cats-photo.jpg"
        private const val MOCK_DESCRIPTION = "Небольшой курс о том, как интегрировать сервисы в ваше приложение или сайт без участия разработчика"
        private const val MOCK_LIST_SIZE = 100
    }
}
