package com.innoprog.android.feature.feed.newsdetails.data.network

import com.innoprog.android.network.data.Response

interface NetworkClient {
    suspend fun getNewsDetails(publicationId: String): Response
    suspend fun getComments(publicationId: String): Response
    suspend fun getProjectDetails(projectId: String): Response
    suspend fun addComment(publicationId: String, content: String): Response
}
