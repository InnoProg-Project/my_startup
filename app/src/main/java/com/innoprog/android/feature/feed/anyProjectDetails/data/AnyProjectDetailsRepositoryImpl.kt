package com.innoprog.android.feature.feed.anyProjectDetails.data

import android.util.Log
import com.innoprog.android.BuildConfig
import com.innoprog.android.feature.feed.anyProjectDetails.data.network.AnyProjectDetailsApi
import com.innoprog.android.feature.feed.anyProjectDetails.data.network.mapToDomain
import com.innoprog.android.feature.feed.anyProjectDetails.domain.AnyProjectDetailsRepository
import com.innoprog.android.feature.feed.anyProjectDetails.domain.models.AnyProjectDetailsModel
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class AnyProjectDetailsRepositoryImpl @Inject constructor(
    private val api: AnyProjectDetailsApi,
) : AnyProjectDetailsRepository {

    override fun getAnyProjectDetails(id: String): Flow<Resource<AnyProjectDetailsModel>> = flow {
        try {
            val response = api.getProjectById(id)
            val body = response.body()
            if (response.code() == ApiConstants.SUCCESS_CODE && body != null) {
                emit(Resource.Success(body.mapToDomain()))
            } else {
                emit(Resource.Error(ErrorType.INTERNAL_SERVER_ERROR))
            }
        } catch (e: HttpException) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, " error: $e")
            }
            emit(Resource.Error(ErrorType.BAD_REQUEST))
        } catch (e: SocketTimeoutException) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, " error: $e")
            }
            emit(Resource.Error(ErrorType.NO_CONNECTION))
        }
    }

    private companion object {
        val TAG = AnyProjectDetailsRepository::class.simpleName
    }
}
