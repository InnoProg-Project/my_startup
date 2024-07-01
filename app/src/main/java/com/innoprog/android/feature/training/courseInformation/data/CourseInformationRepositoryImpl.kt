package com.innoprog.android.feature.training.courseInformation.data

import com.innoprog.android.feature.training.courseInformation.data.network.CourseInformationResponse
import com.innoprog.android.feature.training.courseInformation.data.network.NetworkClient
import com.innoprog.android.feature.training.courseInformation.data.network.mapToCourseInformation
import com.innoprog.android.feature.training.courseInformation.domain.CourseInformationRepository
import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformation
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.SocketTimeoutException
import javax.inject.Inject

class CourseInformationRepositoryImpl @Inject constructor(private val networkClient: NetworkClient) :
    CourseInformationRepository {

    override fun getCourseInformation(courseId: String): Flow<Resource<CourseInformation?>> = flow {
        val response = networkClient.getCourseInformation(courseId)

        runCatching {
            when (response.resultCode) {
                ApiConstants.NO_INTERNET_CONNECTION_CODE -> {
                    emit(Resource.Error(ErrorType.NO_CONNECTION))
                }

                ApiConstants.SUCCESS_CODE -> {
                    with(response as CourseInformationResponse) {
                        val data = results.mapToCourseInformation()
                        emit(Resource.Success(data))
                    }
                }

                else -> {
                    emit(Resource.Error(ErrorType.BAD_REQUEST))
                }
            }
        }.onFailure { exception ->
            if (exception is SocketTimeoutException) {
                emit(Resource.Error(ErrorType.NO_CONNECTION))
            } else {
                emit(Resource.Error(ErrorType.UNEXPECTED))
            }
        }
    }
}

