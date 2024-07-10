package com.innoprog.android.util

import com.innoprog.android.network.data.ApiConstants
import retrofit2.HttpException

class ErrorHandlerImpl : ErrorHandler {
    override fun handleErrorCode(resultCode: Int): Resource.Error {
        return when (resultCode) {
            ApiConstants.BAD_REQUEST -> Resource.Error(ErrorType.BAD_REQUEST)
            ApiConstants.UNAUTHORIZED -> Resource.Error(ErrorType.UNAUTHORIZED)
            ApiConstants.FORBIDDEN -> Resource.Error(ErrorType.FORBIDDEN)
            ApiConstants.NOT_FOUND -> Resource.Error(ErrorType.NOT_FOUND)
            ApiConstants.CONFLICT -> Resource.Error(ErrorType.CONFLICT)
            ApiConstants.UNPROCESSABLE_ENTITY -> Resource.Error(ErrorType.UNPROCESSABLE_ENTITY)
            ApiConstants.INTERNAL_SERVER_ERROR -> Resource.Error(ErrorType.INTERNAL_SERVER_ERROR)
            ApiConstants.BAD_GATEWAY -> Resource.Error(ErrorType.BAD_GATEWAY)
            else -> Resource.Error(ErrorType.UNEXPECTED)
        }
    }

    override fun handleHttpException(exception: HttpException): Resource.Error {
        return when (exception.code()) {
            ApiConstants.BAD_REQUEST -> Resource.Error(ErrorType.BAD_REQUEST)
            ApiConstants.UNAUTHORIZED -> Resource.Error(ErrorType.UNAUTHORIZED)
            ApiConstants.FORBIDDEN -> Resource.Error(ErrorType.FORBIDDEN)
            ApiConstants.NOT_FOUND -> Resource.Error(ErrorType.NOT_FOUND)
            ApiConstants.CONFLICT -> Resource.Error(ErrorType.CONFLICT)
            ApiConstants.UNPROCESSABLE_ENTITY -> Resource.Error(ErrorType.UNPROCESSABLE_ENTITY)
            ApiConstants.INTERNAL_SERVER_ERROR -> Resource.Error(ErrorType.INTERNAL_SERVER_ERROR)
            ApiConstants.BAD_GATEWAY -> Resource.Error(ErrorType.BAD_GATEWAY)
            else -> Resource.Error(ErrorType.UNEXPECTED)
        }
    }
}

