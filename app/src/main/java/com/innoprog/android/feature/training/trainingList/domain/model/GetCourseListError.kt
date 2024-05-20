package com.innoprog.android.feature.training.trainingList.domain.model

enum class GetCourseListError(val code: Int) {
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    SERVER(500),
    NO_CONNECTION(-1)
}