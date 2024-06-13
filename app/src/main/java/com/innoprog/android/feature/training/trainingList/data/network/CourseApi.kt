package com.innoprog.android.feature.training.trainingList.data.network

import com.innoprog.android.feature.training.trainingList.data.network.dto.CourseShortDto
import retrofit2.Response
import retrofit2.http.GET

interface CourseApi {
    @GET("v1/courses")
    suspend fun getCourseList(): Response<List<CourseShortDto>>
}
