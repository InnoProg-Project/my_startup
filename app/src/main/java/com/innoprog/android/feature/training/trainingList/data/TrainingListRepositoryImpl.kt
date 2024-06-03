package com.innoprog.android.feature.training.trainingList.data

import android.util.Log
import com.innoprog.android.feature.training.trainingList.data.network.CourseApi
import com.innoprog.android.feature.training.trainingList.data.network.dto.mapToDomain
import com.innoprog.android.feature.training.trainingList.domain.TrainingListRepository
import com.innoprog.android.feature.training.trainingList.domain.model.CourseShort
import com.innoprog.android.feature.training.trainingList.domain.model.GetCourseListError
import com.innoprog.android.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.SocketTimeoutException
import javax.inject.Inject

class TrainingListRepositoryImpl @Inject constructor(
    private val courseApi: CourseApi
) : TrainingListRepository {
    override fun getTrainingList(): Flow<Result<List<CourseShort>, GetCourseListError>> = flow {
        runCatching {
            val response = courseApi.getCourseList()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                emit(Result.Success(body.courseList.map { it.mapToDomain() }))
            } else {
                when (response.code()) {
                    GetCourseListError.NOT_FOUND.code -> {
                        emit(Result.Error(GetCourseListError.NOT_FOUND))
                    }

                    GetCourseListError.SERVER.code -> {
                        emit(Result.Error(GetCourseListError.SERVER))
                    }

                    GetCourseListError.UNAUTHORIZED.code -> {
                        emit(Result.Error(GetCourseListError.UNAUTHORIZED))
                    }

                    else -> {
                        emit(Result.Error(GetCourseListError.SERVER))
                    }
                }
            }
        }.onFailure { exception ->
            Log.e(TAG, "error -> ${exception.localizedMessage}")
            if (exception is SocketTimeoutException) {
                emit(Result.Error(GetCourseListError.NO_CONNECTION))
            } else {
                emit(Result.Success(mockResponse()))
            }
        }
    }

    private fun mockResponse(): List<CourseShort> {
        val courses = mutableListOf<CourseShort>()
        repeat(MOCK_SIZE) {
            val name = if (it % 2 == 0) {
                "Алексей"
            } else {
                "Унтура Михаил"
            }
            courses.add(CourseShort(
                id = it.toString(),
                direction = "Мобильная разработка",
                title = "Работа с сетью",
                description = "Взаимодействие с Retrofit",
                authorName = name,
                createdDate = "03.06.2024"
            ))
        }
        return courses
    }

    companion object {
        private val TAG = TrainingListRepository::class.simpleName
        private const val MOCK_SIZE = 100
    }
}
