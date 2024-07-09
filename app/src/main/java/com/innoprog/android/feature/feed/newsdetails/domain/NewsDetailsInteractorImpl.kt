package com.innoprog.android.feature.feed.newsdetails.domain

import com.innoprog.android.feature.feed.newsdetails.domain.models.CommentModel
import com.innoprog.android.feature.feed.newsdetails.domain.models.NewsDetailsModel
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsDetailsInteractorImpl @Inject constructor(private val repository: NewsDetailsRepository) :
    NewsDetailsInteractor {
    override suspend fun getNewsDetails(id: String): Resource<NewsDetailsModel> {
        return repository.getNewsDetails(id)
    }

    override fun getComments(newsId: String): Flow<Resource<List<CommentModel>>> {
        return repository.getComments(newsId)
    }

    override suspend fun addComment(
        publicationId: String,
        content: String
    ): Resource<CommentModel> {
        return repository.addComment(publicationId, content)
    }
}
