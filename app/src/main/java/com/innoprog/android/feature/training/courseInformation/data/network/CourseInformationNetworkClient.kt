package com.innoprog.android.feature.training.courseInformation.data.network

import com.innoprog.android.network.data.Response

interface CourseInformationNetworkClient {
    suspend fun getCourseInformation(courseId: String): Response
}