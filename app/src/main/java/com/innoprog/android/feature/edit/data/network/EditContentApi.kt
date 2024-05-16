package com.innoprog.android.feature.edit.data.network

import com.innoprog.android.feature.edit.data.dto.EditDto
import com.innoprog.android.network.data.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST

interface EditContentApi {
    @Multipart
    @POST("/v1/feed")
    suspend fun createContent(
        @Body body: EditDto
    ): Response
}