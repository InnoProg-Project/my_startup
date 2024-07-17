package com.innoprog.android.util

import android.content.Context
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.network.data.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

suspend inline fun <reified D, reified R : Response> getResponse(
    context: Context,
    crossinline requestRunner: suspend () -> retrofit2.Response<D>,
    crossinline mapToResponse: (D) -> R
): Response {
    if (context.isInternetReachable().not()) {
        return Response().apply { resultCode = ApiConstants.NO_INTERNET_CONNECTION_CODE }
    }

    return withContext(Dispatchers.IO) {
        try {
            val response = requestRunner()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                mapToResponse(body).apply { resultCode = response.code() }
            } else {
                Response().apply { resultCode = response.code() }
            }
        } catch (exception: HttpException) {
            Response().apply { resultCode = exception.code() }
        }
    }
}