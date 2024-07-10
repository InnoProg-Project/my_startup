package com.innoprog.android.util

sealed interface Result<Data, Error> {
    class Success<Data, Error>(val data: Data) : Result<Data, Error>
    class Error<Data, Error>(val error: Error) : Result<Data, Error>
}
