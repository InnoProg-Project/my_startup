package com.innoprog.android.feature.training.trainingList.data.network

import com.innoprog.android.feature.training.trainingList.data.network.dto.GetCourseListResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface CourseApi {
    @GET("v1/courses")
    suspend fun getCourseList(): Response<GetCourseListResponse>
}
