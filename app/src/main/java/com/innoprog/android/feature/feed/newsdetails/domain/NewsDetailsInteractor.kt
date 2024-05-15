package com.innoprog.android.feature.feed.newsdetails.domain

import com.innoprog.android.feature.feed.newsdetails.domain.models.NewsDetailsModel
import com.innoprog.android.feature.training.trainingList.domain.ErrorStatus
import kotlinx.coroutines.flow.Flow

interface NewsDetailsInteractor {
    suspend fun getNewsDetails(id: String): Flow<Pair<NewsDetailsModel?, ErrorStatus?>>
}
