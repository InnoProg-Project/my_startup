package com.innoprog.android.feature.feed.newsdetails.domain

import com.innoprog.android.feature.feed.newsdetails.domain.models.CommentModel
import com.innoprog.android.feature.feed.newsdetails.domain.models.NewsDetailsModel
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsDetailsInteractor {
    suspend fun getNewsDetails(id: String): Resource<NewsDetailsModel>
    fun getComments(newsId: String): Flow<Resource<List<CommentModel>>>
}
