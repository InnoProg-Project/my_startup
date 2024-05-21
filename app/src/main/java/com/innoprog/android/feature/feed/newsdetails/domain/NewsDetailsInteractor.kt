package com.innoprog.android.feature.feed.newsdetails.domain

import com.innoprog.android.feature.feed.newsdetails.domain.models.NewsDetailsModel
import com.innoprog.android.feature.training.trainingList.domain.ErrorStatus

interface NewsDetailsInteractor {
    suspend fun getNewsDetails(id: String): Pair<NewsDetailsModel?, ErrorStatus?>
}
