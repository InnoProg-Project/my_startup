package com.innoprog.android.feature.feed.newsdetails.domain

import com.innoprog.android.feature.feed.newsdetails.domain.models.NewsDetailsModel
import com.innoprog.android.feature.training.trainingList.domain.ErrorStatus
import javax.inject.Inject

class NewsDetailsInteractorImpl @Inject constructor(private val repository: NewsDetailsRepository) :
    NewsDetailsInteractor {
    override suspend fun getNewsDetails(id: String): Pair<NewsDetailsModel?, ErrorStatus?> {
        return repository.getNewsDetails(id)
    }
}
