package com.innoprog.android.feature.training.trainingList.domain.model

import com.innoprog.android.network.data.ResponseErrorCode

enum class GetCourseListError(val code: Int) {
    BAD_REQUEST(ResponseErrorCode.BAD_REQUEST),
    UNAUTHORIZED(ResponseErrorCode.UNAUTHORIZED),
    NOT_FOUND(ResponseErrorCode.NOT_FOUND),
    SERVER(ResponseErrorCode.SERVER),
    NO_CONNECTION(ResponseErrorCode.NO_CONNECTION)
}
