package com.innoprog.android.feature.training.courseInformation.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CourseInformationApi {
    @GET("v1/courses/{courseId}")
    suspend fun getCourseInformation(@Path("courseId") courseId: String): Response<CourseInformationDto>
}