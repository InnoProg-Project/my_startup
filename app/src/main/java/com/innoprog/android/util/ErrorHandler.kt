package com.innoprog.android.util

import retrofit2.HttpException

interface ErrorHandler {
    fun handleErrorCode(resultCode: Int): Resource.Error
    fun handleHttpException(exception: HttpException): Resource.Error
}
