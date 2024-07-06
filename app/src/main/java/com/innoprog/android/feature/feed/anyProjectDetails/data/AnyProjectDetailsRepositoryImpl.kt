package com.innoprog.android.feature.feed.anyProjectDetails.data

import android.util.Log
import com.innoprog.android.feature.feed.anyProjectDetails.data.network.AnyProjectDetailsApi
import com.innoprog.android.feature.feed.anyProjectDetails.data.network.mapToDomain
import com.innoprog.android.feature.feed.anyProjectDetails.domain.AnyProjectDetailsRepository
import com.innoprog.android.feature.feed.anyProjectDetails.domain.models.AnyProjectDetailsModel
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
            Log.d("anyProjectDetails_Success", response.code().toString())
            when (response.code()) {
                SUCCESS -> {
                    response.body().let {
                        if (it == null) {
                            emit(Resource.Error(ErrorType.INTERNAL_SERVER_ERROR))
                        } else {
                            emit(Resource.Success(it.mapToDomain()))
                        }
                    }
                }

                else -> emit(Resource.Error(ErrorType.NO_CONNECTION))
            }
        } catch (e: HttpException) {
            Log.e("anyProjectDetails_HttpException", " error: $e")
            emit(Resource.Error(ErrorType.BAD_REQUEST))
        } catch (e: SocketTimeoutException) {
            Log.e("anyProjectDetails_SocketTimeoutException", " error: $e")
            emit(Resource.Error(ErrorType.NO_CONNECTION))
        }
    }

    companion object {
        const val SUCCESS = 200
        const val ERROR = 400
    }
}
