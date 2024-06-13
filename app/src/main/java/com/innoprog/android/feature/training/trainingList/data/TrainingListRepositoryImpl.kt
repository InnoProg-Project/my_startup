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
                emit(Result.Success(body.map { it.mapToDomain() }))
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
            }
        }
    }

    companion object {
        private val TAG = TrainingListRepository::class.simpleName
    }
}
