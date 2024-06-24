package com.innoprog.android.network.data

import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import retrofit2.Response

fun <T> Response<T>.toResource(): Resource<T> {
    return if (isSuccessful) {
        body()?.let {
            Resource.Success(it)
        } ?: Resource.Error(ErrorType.UNKNOWN_ERROR)
    } else {
        val errorType = when (code()) {
            400 -> ErrorType.BAD_REQUEST
            401 -> ErrorType.UNAUTHORIZED
            403 -> ErrorType.FORBIDDEN
            404 -> ErrorType.NOT_FOUND
            500 -> ErrorType.INTERNAL_SERVER_ERROR
            502 -> ErrorType.BAD_GATEWAY
            else -> ErrorType.UNKNOWN_ERROR
        }
        Resource.Error(errorType)
    }
}