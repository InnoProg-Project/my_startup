package com.innoprog.android.util

import kotlinx.coroutines.flow.Flow

typealias ResourcesFlow<T> = Flow<Resource<List<T>>>

sealed class Resource<out T> {

    data class Success<out T>(val data: T) : Resource<T>()

    data class Error(val errorType: ErrorType) : Resource<Nothing>()
}
